package com.chinamobile.checkmes.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinamobile.checkmes.dao.CommonDao;
import com.chinamobile.checkmes.framework.DruidConfig;
import com.chinamobile.checkmes.po.LoginPo;
import com.chinamobile.checkmes.po.companyPo;
import com.chinamobile.checkmes.until.RSAUtil;
import com.chinamobile.checkmes.until.Sha256Until;
import com.chinamobile.checkmes.until.UTF8Until;
import com.chinamobile.checkmes.view.LoginView;
import com.chinamobile.checkmes.view.ResultView;
import com.chinamobile.checkmes.view.SecretView;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.chinamobile.checkmes.until.RSAUtil.deCode;


@Service
public class CommonService {
    @Resource
    private CommonDao commonDao;

    public String apiKey = "44eda2eaf1ce415abe7d813266eb6a62";

    public String secretKey = "6a048751dd3445c897d2ecd18b1dd183";

    //    public String sender="13953111676";
    //获得http接口的密码
    public  String key = DruidConfig.http_key;
    public ResultView send2(String phonrNum, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        SecureRandom rand = new SecureRandom();
        int randNumber =rand.nextInt(9000 ) + 1000;
        LoginPo po=new LoginPo();
        po.setPhone(phonrNum);
        LoginView view=commonDao.checkDate(po);
        String result=null;

        po.setVerCodeTime(System.currentTimeMillis());
       try{
        if(null==view){
            po.setCount(0);
            po.setDate(new java.sql.Date(System.currentTimeMillis()));
            po.setVerCode(Integer.toString(randNumber));
            commonDao.addVerCode(po);
            result= sendMes("【中国移动 行业短信验证】中国移动短信验证系统欢迎您，登录验证码："+randNumber+" 有效期5分钟",key,phonrNum);
        }
        else{
            po.setVerCode(Integer.toString(randNumber));
            commonDao.updateVerCode(po);
            result= sendMes("【中国移动 行业短信验证】中国移动短信验证系统欢迎您，登录验证码："+randNumber+" 有效期5分钟",key,phonrNum);
        }
        if("0".equals(result)){
            ResultView.setResultCode("200");
            ResultView.setResultMes("发送成功");

        }else{
            ResultView.setResultCode("0003");
            ResultView.setResultMes("发送失败");
        }
           }
        catch (Exception e){
           e.printStackTrace();
            ResultView.setResultCode("0002");
            ResultView.setResultMes("发送失败");
        }
        return ResultView;
    }
    public ResultView check2(String phoneNum, String authCode, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        LoginPo po=new LoginPo();
        po.setPhone(phoneNum);
        LoginView view=commonDao.find(po);

        if(null!=view&&authCode.equals(view.getVerCode())){
            if(null!=view.getVerCodeTime()&&(System.currentTimeMillis()-view.getVerCodeTime()>0)&&(System.currentTimeMillis()-view.getVerCodeTime()<60*5*1000)){
                ResultView.setResultCode("200");
                ResultView.setResultMes("验证码校验成功");
            }else {
                ResultView.setResultCode("0004");
                ResultView.setResultMes("验证码已过期");
            }

        }else {

            ResultView.setResultCode("0003");
            ResultView.setResultMes("验证码校验失败");
        }
        return ResultView;
    }

/*    public  String sendMes(String msg, String sender, String deliver) {
        String sendResult = null;
        PostMethod postMethod = null;
        try {
            org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();

            String url = "http://sms.sd.cmcc/09411/SMSSender";
            postMethod = new PostMethod(url);
            postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=GBK");
            postMethod.addParameter("sender", sender);
            postMethod.addParameter("deliver", deliver);
            postMethod.addParameter("msg", msg);
            int status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                sendResult = "发送短信失败！";
            } else {
                sendResult = postMethod.getResponseBodyAsString().toString().trim();
            }
        } catch (Exception err) {
            sendResult = "发送短信失败！";
            err.printStackTrace();
        } finally {
            if (postMethod != null){
                postMethod.releaseConnection(); }// 最后断开服务器连接
        }
        return sendResult;
    }*/


    private String sendMes(String msg, String key,  String phonrNum) {
        try {
            String body = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.yescloudtree.cn:28009");
            post.setHeader("Content-Type", "application/json; charset=utf-8");
            Map params = new HashMap<String, String>();
            params.put("Action", "sendsms");
            params.put("UserName", "zgyd");
            params.put("Password", key);
            params.put("Mobile", phonrNum);
            params.put("Message", base64(msg));
//            params.put("ExtCode", "");
//            params.put("MsgID", "");
//            params.put("IsFlash", "");
//            sendmesView sendmesView = new sendmesView();
//            sendmesView.setpMessage("");
//            sendmesView.setpMobile("");
//            List<sendmesView> list = new ArrayList<>();
//            list.add(sendmesView);
//            params.put("P2pList", list);

            String requestContent = JSONObject.toJSONString(params);
            StringEntity requestEntity = new StringEntity(requestContent, "UTF-8");
            post.setEntity(requestEntity);

            HttpResponse httpResponse = httpClient.execute(post);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity, HTTP.UTF_8);
            }
            httpClient.getConnectionManager().shutdown();
//            System.out.println(body);
            String Status = JSON.parseObject(body).get("Status").toString();//获得状态码
//            JSONObject resultJson = JSONObject.parseObject(body);

//            Map<String, Object> result = (Map<String, Object>) resultJson;
            return Status;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ResultView send(String phonrNum, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        String url = "http://open.home.komect.com/api/v1/sms/authcode/send";
        Map params = new HashMap<String, String>();
        params.put("codeLength", "6");
        params.put("mobile", phonrNum);
        params.put("needReceipt", "0");
        params.put( "messageSign", "短信内容签名");
        params.put("templateId", "2");
        Map<String, Object> result = http(url, params, phonrNum);
        if (null != result.get("resultCode").toString() && result.get("resultCode").toString().length() > 0) {
            String resultCode = result.get("resultCode").toString();
            if ("200".equals(resultCode)) {
                ResultView.setResultCode(resultCode);
                ResultView.setResultMes("");
                HttpSession sessoin = request.getSession();
                sessoin.setAttribute("messageId", result.get("messageId").toString());
            } else if ("421".equals(resultCode)) {
                ResultView.setResultCode(resultCode);
                ResultView.setResultMes("");
            } else {
                ResultView.setResultCode(resultCode);
                ResultView.setResultMes("");
            }
        } else {
            ResultView.setResultCode("500");
            ResultView.setResultMes("");
        }
        System.out.println(ResultView.toString());
        return ResultView;
    }


    public ResultView check(String phoneNum, String authCode, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        String url = "http://open.home.komect.com/api/v1/sms/authcode/verify";
        Map params = new HashMap<String, String>();
        HttpSession session = request.getSession();
        params.put("messageId", session.getAttribute("messageId"));
        params.put("authCode", authCode);
        params.put("mobile", phoneNum);

        Map<String, Object> result = http(url, params, phoneNum);

        if (null != result.get("resultCode").toString() && result.get("resultCode").toString().length() > 0) {
            String resultCode = result.get("resultCode").toString();
            if ("200".equals(resultCode)) {
                ResultView.setResultCode(resultCode);
                ResultView.setResultMes("");
            } else if ("441".equals(resultCode)) {
                ResultView.setResultCode(resultCode);
                ResultView.setResultMes("");
            } else {
                ResultView.setResultCode(resultCode);
                ResultView.setResultMes("");
            }
        } else {
            ResultView.setResultCode("500");
            ResultView.setResultMes("");
        }


        return ResultView;
    }

    public ResultView checkCompany(String phoneNum, String password, HttpServletRequest request) {
        ResultView ResultView = new ResultView();

        companyPo po=commonDao.loginCompany(phoneNum);
        if(null!=po){
            String pass=po.getPassword();
            if(deCode(pass).equals(password)){
                if(po.getStatus()==1){
                    ResultView.setUserKey(po.getPhone());
                    ResultView.setResultCode("200");
                    ResultView.setResultMes("登录成功");
                }
                else if(po.getStatus()==0) {
                    ResultView.setUserKey(po.getPhone());
                    ResultView.setResultCode("205");
                    ResultView.setResultMes("账号处于待审核状态，可前往注册页面修改信息");
                }
                else if(po.getStatus()==2) {
                    ResultView.setUserKey(po.getPhone());
                    ResultView.setResultCode("206");
                    ResultView.setResultMes("账号处于审核失败状态，请前往注册页面修改信息");
                }
                 else{
                    ResultView.setResultCode("203");
                    ResultView.setResultMes("账号或密码有误");
                }
            }
            else{
                ResultView.setResultCode("203");
                ResultView.setResultMes("账号或密码有误");
            }
        }
        else{
            ResultView.setResultCode("204");
            ResultView.setResultMes("账号或密码有误");
        }
        return ResultView;
    }

    public ResultView checkMessage(String mesContent, String dateTime, String phoneNum) {
        boolean flag=true;
        ResultView resultView = new ResultView();
        if (mesContent.indexOf("【密印") != -1 && mesContent.indexOf("】") != -1) {
            flag=false;
            String temp = StringUtils.substringAfter(mesContent, "【密印");
            String secretMes = StringUtils.substringBefore(temp, "】");
            if(secretMes.length()!=11){

                resultView.setResultCode("0002");
                resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
                return resultView;
            }
            String secret = secretMes.substring(0, secretMes.length() - 4);
            String content = StringUtils.substringBefore(mesContent, "【密印");
            String phoneLast1 = secretMes.substring(secretMes.length() - 4, secretMes.length());
            String phoneLast2 = phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
            if (!phoneLast1.equals(phoneLast2)) {
                resultView.setResultCode("0002");
                resultView.setResultMes("验证短信和登录手机号码不匹配，仅支持查询登录手机的短信");
                return resultView;
            } else {
                resultView = checkSeret3(phoneNum, secret, content, dateTime,flag);
            }
        } else if (mesContent.indexOf("[summary") != -1 && mesContent.indexOf("]") != -1) {
            String temp = StringUtils.substringAfter(mesContent, "[summary");
            String secretMes = StringUtils.substringBefore(temp, "]");
            if(secretMes.length()!=11){
                resultView.setResultCode("0002");
                resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
                return resultView;
            }
            String secret = secretMes.substring(0, secretMes.length() - 4);
            String content = StringUtils.substringBefore(mesContent, "[summary");
            String phoneLast1 = secretMes.substring(secretMes.length() - 4, secretMes.length());
            String phoneLast2 = phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
            if (!phoneLast1.equals(phoneLast2)) {
                resultView.setResultCode("0002");
                resultView.setResultMes("验证短信和登录手机号码不匹配，仅支持查询登录手机的短信");
                return resultView;
            } else {
                resultView = checkSeret3(phoneNum, secret, content, dateTime,flag);
            }
        } else {
            resultView.setResultCode("0003");
            resultView.setResultMes("短信内容不完整，请检查短信内容");
        }

        return resultView;
    }
    public ResultView checkMessage2(String mesContent, String dateTime, String phoneNum) {
        boolean flag=true;
        ResultView resultView = new ResultView();
        if (mesContent.indexOf("【密印") != -1 && mesContent.indexOf("】") != -1) {
            flag=false;
            String temp = StringUtils.substringAfter(mesContent, "【密印");
            String secretMes = StringUtils.substringBefore(temp, "】");
            if(secretMes.length()!=11){

                resultView.setResultCode("0002");
                resultView.setResultMes("验证短信和输入手机号码信息不匹配，请检查输入");
                return resultView;
            }
            String secret = secretMes.substring(0, secretMes.length() - 4);
            String content = StringUtils.substringBefore(mesContent, "【密印");
            String phoneLast1 = secretMes.substring(secretMes.length() - 4, secretMes.length());
            String phoneLast2 = phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
            if (!phoneLast1.equals(phoneLast2)) {
                resultView.setResultCode("0002");
                 resultView.setResultMes("验证短信和输入手机号码信息不匹配，请检查输入");
                return resultView;
            } else {
                resultView = checkSeret3(phoneNum, secret, content, dateTime,flag);
            }
        } else if (mesContent.indexOf("[summary") != -1 && mesContent.indexOf("]") != -1) {
            String temp = StringUtils.substringAfter(mesContent, "[summary");
            String secretMes = StringUtils.substringBefore(temp, "]");
            if(secretMes.length()!=11){
                resultView.setResultCode("0002");
                resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
                return resultView;
            }
            String secret = secretMes.substring(0, secretMes.length() - 4);
            String content = StringUtils.substringBefore(mesContent, "[summary");
            String phoneLast1 = secretMes.substring(secretMes.length() - 4, secretMes.length());
            String phoneLast2 = phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
            if (!phoneLast1.equals(phoneLast2)) {
                resultView.setResultCode("0002");
                resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
                return resultView;
            } else {
                resultView = checkSeret3(phoneNum, secret, content, dateTime,flag);
            }
        } else {
            resultView.setResultCode("0003");
            resultView.setResultMes("短信内容不完整，请检查短信内容");
            return resultView;
        }
        return resultView;
    }
    private String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
//将字符串转为base64加密
    public  static  String base64(String content){
        byte[] data= null;
        try {
            data=content.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String newdata=(new BASE64Encoder()).encodeBuffer(data);
        return newdata;
    }

    private Map<String, Object> http(String url, Map<String, String> param, String phoneNum) {
        try {
            String body = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            String time = String.valueOf(System.currentTimeMillis());
            String encodeStr = apiKey + secretKey + time;
            String signStr = MD5(encodeStr);
            Map map = new HashMap<>();
            map.put("apiKey", apiKey);
            map.put("time", time);
            map.put("sign", signStr);
            final byte[] textByte = JSONObject.toJSONBytes(map);
            Base64 base64 = new Base64();
            final String encodedText = new String(base64.encode(textByte));
            post.setHeader("Authorization", encodedText);
            JSONObject jsonObject = JSONObject.parseObject(new String(base64.decode(encodedText)));
            post.setHeader("Content-Type", "application/json; charset=utf-8");
            String requestContent = JSONObject.toJSONString(param);
            StringEntity requestEntity = new StringEntity(requestContent, "UTF-8");
            post.setEntity(requestEntity);
            HttpResponse httpResponse = httpClient.execute(post);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity, HTTP.UTF_8);
            }
            httpClient.getConnectionManager().shutdown();
            System.out.println(body);
            JSONObject resultJson = JSONObject.parseObject(body);

            Map<String, Object> result = (Map<String, Object>) resultJson;
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private ResultView checkSeret(String phone, String secret, String content, String dateTime,boolean flag) {
     byte[] bytes= UTF8Until.utf8to(content);
        ResultView resultView = new ResultView();
        SecretView secretView = new SecretView();
        Integer secretKeynum = Integer.valueOf(secret.substring(0, 1));
        secretView = commonDao.findById(secretKeynum);
        secret = secret.substring(1, secret.length());
        if(isBase64(secret)) {
            if (null != secretView) {

                String secretKey = secretView.getSecret();
                if (secretKey.length()>40) {
                    secretKey = deCode(secretKey);
                } else {
                    secretView.setSecret(RSAUtil.enCode(secretKey));
                    commonDao.updateSecret(secretView);
                }
                Integer summaryLen = secretView.getLength();
                byte[] bytes2 = (phone + dateTime + secretKey).getBytes();
                byte[] byteTemp = byteMerger(bytes, bytes2);
                String pSummary = null;
                if (null != byteTemp) {
                    byte[] byteTemp2 = Sha256Until.getSHA256StrJava(byteTemp);
                    Base64 base64 = new Base64();
                    String encodedText = new String(base64.encode(byteTemp2));
                    pSummary = encodedText.substring(0, summaryLen);
                    if (pSummary.equals(secret)) {
                        resultView.setResultCode("0000");
                        resultView.setResultMes("校验成功");
                    } else {
                        resultView.setResultCode("0005");
                        resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
                    }
                } else {
                    resultView.setResultCode("0007");
                    resultView.setResultMes("验证失败，该短信存在风险，请慎重！");

                }
            } else {
                resultView.setResultCode("0009");
                resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
            }
        }
        else {
            resultView.setResultCode("0009");
            resultView.setResultMes("验证失败，该短信存在风险，请慎重！");
        }
        return resultView;
    }

    private ResultView checkSeret3(String phone, String secret, String content, String dateTime,boolean flag) {
        ResultView resultView = new ResultView();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH时");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHH");
        Date date = new Date();

        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date tempDate = date;
        for (int i = 0; i < 3; i++) {
            resultView = checkSeret(phone, secret, content, sDateFormat.format(date),flag);
            if (resultView.getResultCode().equals("0000")) {
                return resultView;
            } else {
                date = datacut(date);
            }
        }

        for (int i = 0; i < 3; i++) {
            resultView = checkSeret(phone, secret, content, sDateFormat.format(tempDate),flag);
            if (resultView.getResultCode().equals("0000")) {
                return resultView;
            } else {
                tempDate = dataAdd(tempDate);
            }
        }
        return resultView;
    }




    private String bytesToHexStr(byte[] b) {
        if (b == null) {
            return "";
        }
        StringBuffer strBuffer = new StringBuffer(b.length * 3);
        for (int i = 0; i < b.length; i++) {
            strBuffer.append(Integer.toHexString(b[i] & 0xff));
            strBuffer.append(" ");
        }
        return strBuffer.toString().replace("0", "00");
    }

    private static Date datacut(Date date) {
        long a = date.getTime();
        return new Date(a - 3600000);
    }
    private static Date dataAdd(Date date) {
        long a = date.getTime();
        return new Date(a + 3600000);
    }
    public static byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
    private static boolean isBase64(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        } else {
            char[] strChars = str.toCharArray();
            for (char c:strChars) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
                        || c == '+' || c == '/' || c == '=') {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    /*
     *企业为找回密码发送验证码操作
     */
    public ResultView send3(String phonrNum, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        SecureRandom rand = new SecureRandom();
        int randNumber =rand.nextInt(9000 ) + 1000;
        LoginPo po=new LoginPo();
        po.setPhone(phonrNum);
        LoginView view=commonDao.checkcompback(po);
        String result=null;
        po.setVerCodeTime(System.currentTimeMillis());
        try{
            if(null==view){
                po.setDate(new java.sql.Date(System.currentTimeMillis()));
                po.setCount(1);
                po.setVerCode(Integer.toString(randNumber));
                commonDao.addComback(po);
                result= sendMes("【中国移动 行业短信验证】中国移动短信验证系统欢迎您，登录验证码："+randNumber+" 有效期5分钟",key,phonrNum);
            }
            else{
                if (view.getDate().equals(new java.sql.Date(System.currentTimeMillis()).toString())){
                    po.setVerCode(Integer.toString(randNumber));
                    commonDao.updateComback(po);
                    result= sendMes("【中国移动 行业短信验证】中国移动短信验证系统欢迎您，登录验证码："+randNumber+" 有效期5分钟",key,phonrNum);
                }else {
                    po.setCount(1);
                    po.setDate(new java.sql.Date(System.currentTimeMillis()));
                    po.setVerCode(Integer.toString(randNumber));
                    commonDao.updateComback2(po);
                    result= sendMes("【中国移动 行业短信验证】中国移动短信验证系统欢迎您，登录验证码："+randNumber+" 有效期5分钟",key,phonrNum);
                }
            }
            if("0".equals(result)){
                ResultView.setResultCode("200");
                ResultView.setResultMes("发送成功");

            }else{
                ResultView.setResultCode("0003");
                ResultView.setResultMes("发送失败");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ResultView.setResultCode("0002");
            ResultView.setResultMes("发送失败");
        }
        return ResultView;
    }

    /*在重置密码页面中判断账号与验证码是否匹配*/
    public ResultView check3(String phoneNum, String authCode, HttpServletRequest request) {
        ResultView ResultView = new ResultView();
        LoginPo po=new LoginPo();
        po.setPhone(phoneNum);
        LoginPo view=commonDao.findcomback(po);
        if(null!=view&&authCode.equals(view.getVerCode())){
            if(null!=view.getVerCodeTime()&&(System.currentTimeMillis()-view.getVerCodeTime()>0)&&(System.currentTimeMillis()-view.getVerCodeTime()<60*5*1000)){
                ResultView.setResultCode("200");
                ResultView.setResultMes("短信验证码校验成功");
            }else {
                ResultView.setResultCode("0004");
                ResultView.setResultMes("短信验证码已过期");
            }

        }else {

            ResultView.setResultCode("0003");
            ResultView.setResultMes("短信验证码校验失败");
        }
        return ResultView;
    }

}
