package com.magic.express.service;

import com.magic.express.exception.BusinessException;
import com.magic.express.model.Income;
import com.magic.express.repository.IncomeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@Service
public class IncomeService {
    @Resource
    private IncomeDao incomeDao;

    public void save(Income income) throws BusinessException {
        incomeDao.insert(income);
    }

    public Object list() throws BusinessException {
        return incomeDao.list();
    }
}
