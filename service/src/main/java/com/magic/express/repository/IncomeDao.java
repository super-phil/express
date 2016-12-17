package com.magic.express.repository;

import com.magic.express.exception.BusinessException;
import com.magic.express.model.Income;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@Repository
public class IncomeDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * <option value="x">现金</option>
     * <option value="y">月结</option>
     * <option value="w">微信</option>
     * <option value="q">欠款</option>
     * <option value="d">代收</option>
     */
    public void insert(Income income) throws BusinessException {
        try {
            jdbcTemplate.update("INSERT INTO income (x,y,w,q,d,create_time) VALUES (?,?,?,?,?,?)", new Object[]{income.getX(), income.getY(), income.getW(), income.getQ(), income.getD(), income.getCreateTime()});
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    public Object list() throws BusinessException {
        try {
            return jdbcTemplate.queryForList("SELECT * FROM income ORDER BY id DESC ");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
}
