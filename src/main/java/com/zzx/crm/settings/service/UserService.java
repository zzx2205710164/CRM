package com.zzx.crm.settings.service;

import com.zzx.crm.exception.LoginExpection;
import com.zzx.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginExpection;

    List<User> getUserList();
}
