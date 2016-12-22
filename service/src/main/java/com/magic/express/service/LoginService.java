package com.magic.express.service;

import com.magic.express.exception.BusinessException;
import com.magic.express.repository.LoginDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@Service
public class LoginService {
    @Resource
    private LoginDao loginDao;

    public Object login(String userName, String password) throws BusinessException {
        return loginDao.find(userName, password);
    }

}
