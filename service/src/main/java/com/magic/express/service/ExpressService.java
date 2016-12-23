package com.magic.express.service;

import com.magic.express.exception.BusinessException;
import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import com.magic.express.repository.ExpressDao;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/16 12:26
 *          ExpressService
 */
@Service
public class ExpressService {
    @Resource
    private ExpressDao expressDao;

    public void save(Express express) throws BusinessException {
        expressDao.save(express);
    }

    public void save(List<Express> express) throws BusinessException {
        expressDao.save(express);
    }

    /**
     * 根据快递号分页
     *
     * @param dtRequest 分页参数
     * @param type      收款类型
     * @return
     */
    public DTResponse<Map<String, Object>> findByQ(DTRequest dtRequest, String type) throws BusinessException {
        return expressDao.findByQ(dtRequest, type);
    }

    public List<Map<String, Object>> chartsPrice(int days) throws BusinessException {
        return expressDao.chartsPrice(days);
    }


    public List<Map<String, Object>> chartByType() throws BusinessException {
        return expressDao.chartByType();
    }

    public List<Map<String, Object>> chartByType(DateTime dateTime) throws BusinessException {
        return expressDao.chartByType(dateTime);
    }

    public void del(String id) throws BusinessException {
        expressDao.del(id);
    }

    /**
     * 变更欠款到现金
     *
     * @param id id
     */
    public void updateTypeToX(String id) throws BusinessException {
        expressDao.updateTypeToX(id);
    }

    public Object updateStatus(String id) throws BusinessException {
        return expressDao.updateStatus(id);
    }

    public Object updateInfo(String id, int price, String type, String desc) throws BusinessException {
        return expressDao.updateInfo(id, price, type, desc);
    }

    public Object updateUrl(String number, String url) throws BusinessException {
        return expressDao.updateUrl(number,url);
    }
}
