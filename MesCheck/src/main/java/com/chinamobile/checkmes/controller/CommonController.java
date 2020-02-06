package com.chinamobile.checkmes.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.checkmes.dao.CommonDao;
import com.chinamobile.checkmes.po.LoginPo;
import com.chinamobile.checkmes.po.companyPo;
import com.chinamobile.checkmes.po.glPo;
import com.chinamobile.checkmes.service.CommonService;
import com.chinamobile.checkmes.until.IpUntil;
import com.chinamobile.checkmes.until.RSAUtil;
import com.chinamobile.checkmes.until.RandomValidateCodeUtil;
import com.chinamobile.checkmes.view.LoginView;
import com.chinamobile.checkmes.view.ResultView;
import com.chinamobile.checkmes.view.registerCom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;


@Controller
public class CommonController {
    public static final String REGEX_MOBILE="^1[0-9]{10}$";
    @Resource
    private CommonService commonService;
    @Resource
    private CommonDao commonDao;

    @RequestMapping("/register")
    public ModelAndView register(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mv = new ModelAndView();
        companyPo companyPo = new companyPo();
        companyPo.setPhone("1");
        if (null != session.getAttribute("user") && !"0".equals(session.getAttribute("user").toString())) {
            companyPo = commonDao.loginCompany(session.getAttribute("user").toString());
  }
        mv.addObject("companyPo", companyPo);
        mv.setViewName("register");
        return mv;
    }
    @RequestMapping("/check")
    public ModelAndView check(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mv = new ModelAndView();

     if(null!=session.getAttribute("type")&&session.getAttribute("type").toString().equals("2")){
         mv.addObject("flag","2");
     }
     else if(null!=session.getAttribute("type")&&session.getAttribute("type").toString().equals("0")){
         mv.addObject("flag","0");
     }
     else {
         mv.addObject("flag","1");
     }
        mv.setViewName("check");
        return mv;
    }

    @RequestMapping("/examine")
    public ModelAndView examine(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        if(null!=session.getAttribute("type")&&session.getAttribute("type").toString().equals("0")){
        mv.setViewName("examine");
        List<companyPo> list = commonDao.findList();
        for (companyPo po : list) {
            int i = po.getStatus();
            if (i == 0) {
                po.setStatusVlaue("未审核");
            } else if (i == 1) {
                po.setStatusVlaue("审核成功");
            } else {
                po.setStatusVlaue("已驳回");
            }

        }
        mv.addObject("list", list);}
        else {
            mv.setViewName("index");
        }
        return mv;
    }


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    public static String flag = "2";

    @GetMapping("/getVerCode")
    @ResponseBody
    public ResultView getCerCode(String phoneNum, HttpServletRequest request) {
        ResultView resultView = new ResultView();
        /*控制发送短信的时间间隔*/
        LoginPo po=new LoginPo();
        po.setPhone(phoneNum);
        LoginView view=commonDao.find(po);
        if (view==null){
            if (flag.equals("1")) {
                return commonService.send(phoneNum, request);
            } else {
                return commonService.send2(phoneNum, request);
            }
        }else if (System.currentTimeMillis()>(view.getVerCodeTime()+60000)){
            if (flag.equals("1")) {
                return commonService.send(phoneNum, request);
            } else {
                return commonService.send2(phoneNum, request);
            }
        }else {
            /*若直接调用后台的接口进行短信发送*/
            resultView.setResultCode("0009");
            resultView.setResultMes("发送短信过于频繁，发送失败");
        }
        return  resultView;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultView login(String phoneNum, String authCode, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        if (flag.equals("1")) {
            ResultView = commonService.check(phoneNum, authCode, request);
        } else {
            ResultView = commonService.check2(phoneNum, authCode, request);
        }
        HttpSession session = request.getSession();
        LoginPo po = new LoginPo();
        po.setDate(new Date(System.currentTimeMillis()));
        po.setIp(IpUntil.getIpAddress(request));
        po.setPhone(phoneNum);
        LoginView loginview = commonDao.checkDate(po);
        glPo gl=new glPo();
        gl=commonDao.findGl(phoneNum);
        session.setAttribute("type","1");
        if ("200".equals(ResultView.getResultCode())) {
            if (loginview.getDate().equals(new Date(System.currentTimeMillis()).toString())) {
                if (loginview.getCount() <= 5) {
                    session.setAttribute("user", phoneNum);
                    session.setAttribute("ip", IpUntil.getIpAddress(request));
                    if(null!=gl){
                        session.setAttribute("type","0");
                    }

                } else {
                    ResultView.setResultCode("0009");
                    ResultView.setResultMes("登录错误次数已达今日上线，请明日再试");
                    return ResultView;
                }
            } else {
                session.setAttribute("user", phoneNum);
                session.setAttribute("ip", IpUntil.getIpAddress(request));
                if(null!=gl){
                    session.setAttribute("type","0");
                }
            }

        } else {
            ResultView.setResultCode("0008");
            session.setAttribute("user", "0");
            if (null == loginview) {
                po.setCount(1);
                commonDao.addLogin(po);
            } else {
                if (loginview.getDate().equals(new Date(System.currentTimeMillis()).toString())) {
                    if (loginview.getCount() <= 5) {
                        commonDao.updateCount(po);
                    } else {
                        ResultView.setResultCode("0009");
                        ResultView.setResultMes("登录错误次数已达今日上限，请明日再试");
                        return ResultView;
                    }
                } else {
                    po.setCount(1);
                    commonDao.updateDate(po);
                }
            }
        }
        return ResultView;
    }

    @PostMapping("/checkMessage")
    @ResponseBody
    public ResultView checkMessage(String phone,String mesContent, String dateTime, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String phoneNum = session.getAttribute("user").toString();
        ResultView ResultView = new ResultView();
        if(null!=session.getAttribute("type")&&session.getAttribute("type").toString().equals("2")){
            phoneNum=phone;
            if(null==phoneNum||phoneNum.length()<1||phoneNum.equals("")||!Pattern.matches(REGEX_MOBILE,phoneNum)){
                 ResultView.setResultCode("0001");
                 ResultView.setResultMes("手机号码格式错误，请检查输入");
                 return ResultView;
            }
            return commonService.checkMessage2(mesContent, dateTime, phoneNum);
        }
        return commonService.checkMessage(mesContent, dateTime, phoneNum);
    }

    @PostMapping("/loginCompany")
    @ResponseBody
    public ResultView loginCompany(String phoneNum, String password,String inputStr, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        HttpSession session = request.getSession();
        try {
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                ResultView.setResultCode("201");
                ResultView.setResultMes("验证码错误,请重新输入");
            }
            if (random.equals(inputStr)) {
                ResultView.setResultCode("200");
            } else {
                ResultView.setResultCode("203");
                ResultView.setResultMes("验证码错误,请重新输入");
            }
        } catch (Exception e) {
            ResultView.setResultCode("202");
            ResultView.setResultMes("验证码错误,请重新输入");
        }
        session.setAttribute("RANDOMVALIDATECODEKEY","0");

        if(ResultView.getResultCode().equals("200")){
            LoginPo po = new LoginPo();
            po.setDate(new Date(System.currentTimeMillis()));
            po.setIp(IpUntil.getIpAddress(request));
            po.setComPhone(phoneNum);
            LoginView loginview = commonDao.checkDate2(po);
            if (null != loginview && loginview.getDate().equals(new Date(System.currentTimeMillis()).toString()) && loginview.getCount() > 5) {
                ResultView.setResultCode("0009");
                ResultView.setResultMes("登录错误次数已达今日上线，请明日再试");
                return ResultView;
            } else {
                ResultView = commonService.checkCompany(phoneNum, password, request);
                if ("200".equals(ResultView.getResultCode())) {
                    session.setAttribute("user", ResultView.getUserKey());
                    session.setAttribute("ip", IpUntil.getIpAddress(request));
                    session.setAttribute("type","2");
                }
                else if("205".equals(ResultView.getResultCode())||"206".equals(ResultView.getResultCode())){
                    session.setAttribute("user2", ResultView.getUserKey());
                    session.setAttribute("ip", IpUntil.getIpAddress(request));

                }
                else {
                    session.setAttribute("user", "0");
                    if (null == loginview) {
                        po.setCount(1);
                        commonDao.addLogin2(po);
                    } else {
                        if (loginview.getDate().equals(new Date(System.currentTimeMillis()).toString())) {
                            commonDao.updateCount2(po);

                        } else {
                            po.setCount(1);
                            commonDao.updateDate2(po);
                        }
                    }
                }
            }
        }

        return ResultView;
    }

    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/lougout")
    @ResponseBody
    public ResultView lougOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        ResultView ResultView = new ResultView();
        ResultView.setResultCode("0000");
        return ResultView;
    }

    @RequestMapping(value = "/registerCom", method = RequestMethod.POST)
    @ResponseBody
    public ResultView registerCom(registerCom input, HttpServletRequest request) throws Exception {
        ResultView ResultView = new ResultView();
        if (!input.getPassword().equals(input.getPassword2())) {
            ResultView.setResultCode("0000");
            ResultView.setResultMes("两次输入密码不一致请重新输入");
            return ResultView;
        }
        companyPo po = new companyPo();
        po.setAdress(input.getAdress());
        po.setCpName(input.getCpName());
        po.setStatus(0);
        po.setPassword(RSAUtil.enCode(input.getPassword()));
        po.setPhone(input.getPhone());
        po.setName(new String(input.getName()));
        HttpSession session = request.getSession();
        Object phoneLogin = session.getAttribute("user2");
        if(null!=input.getComPanyID()){   po.setComPanyID(input.getComPanyID());}
        if (null != phoneLogin && !phoneLogin.equals("0")&&phoneLogin.equals(input.getPhone())) {
                commonDao.updateCom(po);
                ResultView.setResultCode("200");
                ResultView.setResultMes("修改账户信息成功，请等待审核，您的手机号为登录账号");
        } else {
            companyPo pocheck = commonDao.loginCompany(input.getPhone());
            companyPo pocheck2 = commonDao.loginCompany2(input.getCpName());
            if (null == pocheck) {
                if (null == pocheck2) {
                    commonDao.addCom(po);
                    ResultView.setResultCode("200");
                    ResultView.setResultMes("注册成功，请等待审核，您的手机号为登录账号");
                } else {
                    ResultView.setResultCode("0004");
                    ResultView.setResultMes("该企业名称已经注册，请登录后修改");
                }

            } else {
                ResultView.setResultCode("0004");
                ResultView.setResultMes("该手机号码已经注册，请登录后修改");
            }
        }


        return ResultView;
    }
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    @ResponseBody
    public ResultView updateOne(String comId) {
        ResultView ResultView = new ResultView();
        companyPo po=new companyPo();
        po.setStatus(1);
        po.setComPanyID(comId);
        companyPo checkPo =new companyPo();
        checkPo=commonDao.findById2(comId);
        if(null!=checkPo){
            if(checkPo.getStatus()==0){
                commonDao.updateComOne(po);

                ResultView.setResultCode("200");
                ResultView.setResultMes("审核成功");
            }
            else {
                ResultView.setResultCode("0001");
                ResultView.setResultMes("该账号已审核不可重复审核");
            }
        }
        else{
            ResultView.setResultCode("0000");
            ResultView.setResultMes("账号不存在");
        }
        return ResultView;
    }
    @RequestMapping(value = "/updateTwo", method = RequestMethod.POST)
    @ResponseBody
    public ResultView updateTwo(String comId) {
        ResultView ResultView = new ResultView();
        companyPo po=new companyPo();
        po.setStatus(2);
        po.setComPanyID(comId);
        companyPo checkPo =new companyPo();
        checkPo=commonDao.findById2(comId);
        if(null!=checkPo){
            if(checkPo.getStatus()==0){
                commonDao.updateComOne(po);
             ResultView.setResultCode("200");
                ResultView.setResultMes("审核成功");
            }
            else {
                ResultView.setResultCode("0001");
                ResultView.setResultMes("该账号已审核不可重复审核");
            }
        }
        else{
            ResultView.setResultCode("0000");
            ResultView.setResultMes("账号不存在");
        }

        return ResultView;
    }

    @RequestMapping(value = "/allowAll", method = RequestMethod.POST)
    @ResponseBody
    public ResultView allowAll( String[] list) {
        ResultView ResultView = new ResultView();
        if(null!=list&&list.length>0){
            commonDao.updateList(list);
            ResultView.setResultCode("200");
            ResultView.setResultMes("审核成功");
        }
        else {

            ResultView.setResultCode("0001");
            ResultView.setResultMes("审核失败");
        }

        return ResultView;
    }
    @RequestMapping(value = "/refuseAll", method = RequestMethod.POST)
    @ResponseBody
    public ResultView refuseAll( String[] list) {
        ResultView ResultView = new ResultView();
        if(null!=list){
            commonDao.updateList2(list);
            ResultView.setResultCode("200");
            ResultView.setResultMes("审核成功");
        }
        else {

            ResultView.setResultCode("0001");
            ResultView.setResultMes("审核失败");
        }

        return ResultView;
    }



    @RequestMapping("/forgetpassword")
    public String return_forgetpassword(){
        return "forgetpassword";
    }

    @RequestMapping("/checkphone")
    @ResponseBody
    public ResultView checkphone(String phone){
        ResultView resultView =  new ResultView();
        companyPo pocheck = commonDao.loginCompany(phone);
        if (null == pocheck) {
            resultView.setResultCode("0001");
            resultView.setResultMes("信息填写有误，请检查输入");
        } else {
            resultView.setResultCode("200");
        }
        return resultView;
    }

    @GetMapping("/getcompVerCode")
        @ResponseBody
        public ResultView getcompVerCode(String phoneNum, HttpServletRequest request) {
            ResultView resultView = new ResultView();
            /*控制发送短信的时间间隔*/
            LoginPo po=new LoginPo();
            po.setPhone(phoneNum);
            LoginPo loginPo=commonDao.findcomback(po);
            if (loginPo==null){
                resultView = commonService.send3(phoneNum, request);
            }else if (System.currentTimeMillis()>(loginPo.getVerCodeTime()+50000)){
                resultView = commonService.send3(phoneNum, request);
            }else {
                /*若直接调用后台的接口进行短信发送*/
                resultView.setResultCode("0009");
                resultView.setResultMes("发送短信过于频繁，发送失败");
            }
            return  resultView;

    }


    @RequestMapping("/editpassword")
    @ResponseBody
    public ResultView editpassword(String phoneNum, String authCode,String verify_input, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        HttpSession session = request.getSession();
        session.setAttribute("backpasswordphone",phoneNum);
        try {
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                ResultView.setResultCode("201");
                ResultView.setResultMes("图形验证码错误,请重新输入");
            }
            if (random.equals(verify_input)) {
                ResultView.setResultCode("200");
                ResultView = commonService.check3(phoneNum, authCode, request);
            } else {
                ResultView.setResultCode("203");
                ResultView.setResultMes("图形验证码错误,请重新输入");
            }
        } catch (Exception e) {
            ResultView.setResultCode("202");
            ResultView.setResultMes("图形验证码错误,请重新输入");
        }
        session.setAttribute("RANDOMVALIDATECODEKEY","0");
        /*判断发送重置密码短信的次数*/
        LoginPo po = new LoginPo();
        po.setDate(new Date(System.currentTimeMillis()));
        po.setPhone(phoneNum);
        LoginPo loginview = commonDao.findcomback(po); //返回此手机号的date与count
        if (loginview.getDate().toString().equals(new Date(System.currentTimeMillis()).toString())) {
            if (loginview.getCount() <= 5) {
                if ("200".equals(ResultView.getResultCode())){
                    ResultView.setResultCode("200");
                }else {
                    ResultView.setResultCode("0003");
                    ResultView.setResultMes("校验失败");
                }
            } else {
                ResultView.setResultCode("0009");
                ResultView.setResultMes("登录错误次数已达今日上线，请明日再试");
                return ResultView;
            }
        } else {
            if ("200".equals(ResultView.getResultCode())) {
                ResultView.setResultCode("200");
            }else {
                ResultView.setResultCode("0003");
                ResultView.setResultMes("校验失败");
            }
        }


        return ResultView;
    }

    @RequestMapping("/editpasswordindex")
    public String editpasswordindex(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("backpasswordphone").toString()==null){
            return "index";
        }else {
            return "editpassword";
        }

    }

    @RequestMapping("/editpass")
    @ResponseBody
    public  ResultView editpass(registerCom registerCom, HttpServletRequest request){
        ResultView resultView = new ResultView();
        HttpSession session = request.getSession();
        if (session.getAttribute("backpasswordphone").toString()!=null){
            if(!registerCom.getPassword().equals(registerCom.getPassword2())||registerCom.getPassword()==null){
                resultView.setResultMes("两次输入密码不一致，请重新输入");
                resultView.setResultCode("0001");
            }else {
                companyPo companyPo = new companyPo();
                companyPo.setPhone(session.getAttribute("backpasswordphone").toString());
                companyPo.setPassword(RSAUtil.enCode(registerCom.getPassword()));
                commonDao.editpass(companyPo);
                resultView.setResultMes("修改成功");
                resultView.setResultCode("200");
            }
        }else {
            resultView.setResultMes("非法修改，请检查后再次修改");
            resultView.setResultCode("0009");
        }

        return resultView;
    }

}
