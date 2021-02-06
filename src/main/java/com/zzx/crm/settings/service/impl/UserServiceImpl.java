package com.zzx.crm.settings.service.impl;

import com.zzx.crm.exception.LoginExpection;
import com.zzx.crm.settings.dao.UserDao;
import com.zzx.crm.settings.domain.User;
import com.zzx.crm.settings.service.UserService;
import com.zzx.crm.utils.DateTimeUtil;
import com.zzx.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public User login(String loginAct, String loginPwd, String ip) throws LoginExpection {

        Map<String,String> map = new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if (user==null){

            throw new LoginExpection("账号密码错误");

        }

        //如果程序能够成功的执行到改行，说明账号密码正确
        //需要继续向下验证其他三项信息

        //验证失效时间
        String expirTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expirTime.compareTo(currentTime)<0){

            throw new LoginExpection("账号已失效");

        }

        //判断锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginExpection("账号已锁定，请联系管理员");
        }

        //判断ip地址
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginExpection("ip地址受限");
        }

        return user;
    }

    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();

        return userList;
    }
}
