package com.chinamobile.checkmes.dao;

import com.chinamobile.checkmes.po.LoginPo;
import com.chinamobile.checkmes.po.companyPo;
import com.chinamobile.checkmes.po.glPo;
import com.chinamobile.checkmes.view.LoginView;
import com.chinamobile.checkmes.view.SecretView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonDao {
    void addVerCode(LoginPo po);
    void addLogin(LoginPo po);
    LoginView find(LoginPo po);
    void  updateVerCode(LoginPo po);
    void addLogin2(LoginPo po);
    LoginView checkDate(LoginPo po);
    LoginView checkDate2(LoginPo po);
    void updateCount(LoginPo po);
    void updateDate(LoginPo po);
    void updateCount2(LoginPo po);
    void updateDate2(LoginPo po);
    SecretView findById(Integer id);
    void updateSecret(SecretView view);
    companyPo loginCompany(String phone);
    companyPo loginCompany2(String phone);
    void updateCom(companyPo po);
    void addCom(companyPo po);
    List<companyPo> findList();
    glPo findGl(String userName);
    void updateComOne(companyPo po);
    companyPo findById2(String comPanyID);
    void updateList2(String[] list);
    void updateList(String[] list);

    LoginView checkcompback(LoginPo loginPo);
    void addComback(LoginPo loginPo);
    void updateComback(LoginPo loginPo);
    void updateComback2(LoginPo loginPo);
    LoginPo findcomback(LoginPo loginPo);
    void editpass(companyPo companyPo);
}
