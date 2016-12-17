package com.magic.express.repository;

import com.magic.express.common.Constant;
import com.magic.express.exception.BusinessException;
import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
        list.forEach(this::insert);
    }

    public void save(Express express) throws BusinessException {
        insert(express);
    }

    private void insert(Express express) throws BusinessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO express (`id`,`number`,`desc`,`type`,`price`) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, express.getUserId());
                statement.setString(2, express.getNumber());
                statement.setString(3, express.getDesc());
                statement.setString(4, express.getType());
                statement.setInt(5, express.getPrice());
                return statement;
            }, keyHolder);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        express.setId(keyHolder.getKey().longValue());
    }

    /**
     * 统计最近N天 现收及数量
     *
     * @param days 最近N天
     * @return
     */
    public List<Map<String, Object>> chartsPrice(int days) throws BusinessException {
        String s = "SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS create_time, SUM(price) AS sum, COUNT(1) AS count FROM express WHERE create_time>='" + DateTime.now().minusDays(days).toString("yyyy-MM-dd") + "' AND TYPE='s' GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')";
        try {
            return jdbcTemplate.queryForList(s);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 根据类型汇总
     *
     * @return
     */
    public List<Map<String, Object>> chartByType() {
        String s = "SELECT SUM(price) AS sum, TYPE AS type FROM express WHERE DATE_FORMAT(create_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d') GROUP BY type";
        try {
            return jdbcTemplate.queryForList(s);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 根据类型汇总
     *
     * @return
     */
    public List<Map<String, Object>> chartByType(DateTime dateTime) {
        String s = "SELECT SUM(price) AS sum, type AS type FROM express WHERE DATE_FORMAT(create_time,'%Y-%m-%d')='" + dateTime.toString("yyyy-MM-dd") + "' GROUP BY type";
        try {
            return jdbcTemplate.queryForList(s);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 根据订单号 查询分页数据
     *
     * @param dtRequest 查询参数
     * @param type
     * @return
     */
    public DTResponse<Map<String, Object>> findByQ(DTRequest dtRequest, String type) throws BusinessException {
        String q;
        String qs;
        String qc;
        if (!StringUtils.isEmpty(dtRequest.getQ())) {
            q = "%" + dtRequest.getQ() + "%";
            qs = "SELECT * FROM express WHERE number LIKE ? ORDER BY create_time DESC,price DESC LIMIT ?,?";
            qc = "SELECT count(1) FROM express WHERE number LIKE ? ";
        } else {
            q = type;
            qs = "SELECT * FROM express WHERE type=? ORDER BY create_time DESC,price DESC LIMIT ?,?";
            qc = "SELECT count(1) FROM express WHERE type = ? ";
        }

        try {
            Integer count = jdbcTemplate.queryForObject(qc, new Object[]{q}, Integer.class);
            DTResponse<Map<String, Object>> response = new DTResponse<>();
            if (count != null && count > 0) {
                List<Map<String, Object>> mapList = jdbcTemplate.queryForList(qs, q, dtRequest.getStart(), dtRequest.getLength());
                response.setRecordsTotal(count);
                response.setRecordsFiltered(count);
                response.setData(mapList);
            } else {
                response.setRecordsTotal(0);
                response.setRecordsFiltered(0);
                response.setData(null);
            }
            response.setDraw(dtRequest.getDraw());
            return response;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void del(String id) throws BusinessException {
        try {
            jdbcTemplate.update("DELETE FROM express WHERE id=?", id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 变更欠款到现金
     *
     * @param id id
     */
    public void updateTypeToX(String id) {
        try {
            jdbcTemplate.update("UPDATE express SET type=? WHERE id=?", Constant.Type.X.name().toLowerCase(), id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
