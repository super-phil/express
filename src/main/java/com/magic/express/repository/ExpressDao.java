package com.magic.express.repository;

import com.magic.express.exception.BusinessException;
import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
    
    public void save(List<Express> list) throws BusinessException {
        try{
            list.forEach(this::insert);
        }catch(Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    
    public void save(Express express) throws BusinessException {
        try{
            insert(express);
        }catch(Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    
    private void insert(Express express) {
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement=connection.prepareStatement("INSERT INTO express (number,url,type,price,create_time) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, express.getNumber());
            statement.setString(2, express.getUrl());
            statement.setString(3, express.getType());
            statement.setInt(4, express.getPrice());
            statement.setDate(5, new Date(express.getCreateTime().getTime()));
            return statement;
        }, keyHolder);
        express.setId(keyHolder.getKey().longValue());
    }
    
    /**
     * 统计最近N天 现收及数量
     *
     * @param days 最近N天
     * @return
     */
    public List<Map<String,Object>> chartsPrice(int days) throws BusinessException {
        String s="SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS create_time, SUM(price) AS sum, COUNT(1) AS count FROM express WHERE create_time>='"+DateTime.now().minusDays(days).toString("yyyy-MM-dd")+"' AND TYPE='s' GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')";
        try{
            return jdbcTemplate.queryForList(s);
        }catch(Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * 根据订单号 查询分页数据
     *
     * @param dtRequest 查询参数
     * @return
     */
    public DTResponse<Map<String,Object>> findByNumberLike(DTRequest dtRequest) throws BusinessException {
        String q="%"+dtRequest.getQ()+"%";
        String s="SELECT * FROM express WHERE number LIKE ? ORDER BY type DESC ,create_time DESC,price DESC";
        try{
            List<Map<String,Object>> mapList=jdbcTemplate.queryForList(s, new Object[]{q});
            DTResponse<Map<String,Object>> response=new DTResponse<>();
            response.setRecordsTotal(mapList.size());
            response.setRecordsFiltered(mapList.size());
            response.setDraw(dtRequest.getDraw());
            response.setData(mapList);
            return response;
        }catch(Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
