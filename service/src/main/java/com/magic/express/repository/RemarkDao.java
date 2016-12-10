package com.magic.express.repository;

import com.magic.express.exception.BusinessException;
import com.magic.express.model.Remark;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoXiuFei on 2016/12/10.
 */
@Repository
public class RemarkDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void save(Remark remark) throws BusinessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO remark (`text`) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, remark.getText());
                return statement;
            }, keyHolder);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        remark.setId(keyHolder.getKey().longValue());
    }

    public void del(String id) throws BusinessException {
        try {
            jdbcTemplate.update("DELETE FROM remark WHERE id=?", id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<Map<String, Object>> list() {
        String s = "SELECT * FROM remark";
        try {
            return jdbcTemplate.queryForList(s);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
