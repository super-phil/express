package com.magic.express.repository;

import com.magic.express.exception.BusinessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@Repository
public class LoginDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Object find(String userName, String password) throws BusinessException {
        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM `user` WHERE `username`=? AND `password`=?", new Object[]{userName, password});
            return maps.size() == 0 ? null : maps.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
}
