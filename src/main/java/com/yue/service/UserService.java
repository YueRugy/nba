package com.yue.service;

import com.yue.constant.UserCode;
import com.yue.dao.UserDao;
import com.yue.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yue on 2018/5/16
 */
@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User findByUsernameAndPassword(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return null;
        }
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    public int register(User user) {
        if (user.getPassword() == null) {
            return UserCode.passwordEmpty.getCode();
        }
        if (user.getUsername() == null) {
            return UserCode.usernameEmpty.getCode();
        }

        User userData = userDao.findByUsername(user.getUsername());
        if (userData != null) {
            return UserCode.usernameExists.getCode();
        }
        userDao.save(user);
        return UserCode.success.getCode();
    }
}
