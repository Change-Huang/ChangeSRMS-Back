package com.change.changesrmsback.service;

import com.change.changesrmsback.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Change
 */
@Service
public class LoginService {

    private UserDao userDao;

    @Autowired
    public void UserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String test() {
        return userDao.selectTest();
    }
}
