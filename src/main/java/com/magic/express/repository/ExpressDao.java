package com.magic.express.repository;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/21 12:20
 *          ExpressDao
 */
@Repository
public class ExpressDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    public List<Map<String,Object>> chartsPrice(int days) {
        String s="SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS create_time, SUM(price) AS sum, COUNT(1) AS count FROM express WHERE create_time>='"+DateTime.now().minusDays(days).toString("yyyy-MM-dd")+"' AND TYPE='s' GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')";
        return jdbcTemplate.queryForList(s);
        
    }
}
