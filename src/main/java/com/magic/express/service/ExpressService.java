package com.magic.express.service;

import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import com.magic.express.repository.ExpressDao;
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
    public void save(Express express) {
        expressDao.save(express);
    }
    
    public void save(List<Express> express) {
        expressDao.save(express);
    }
    
    /**
     * 根据快递号分页
     *
     * @param dtRequest 分页参数
     * @return
     */
    public DTResponse<Map<String,Object>> findByNumberLike(DTRequest dtRequest) {
        return expressDao.findByNumberLike(dtRequest);
    }
    
    public List<Map<String,Object>> chartsPrice(int days) {
        return expressDao.chartsPrice(days);
    }
    
    
}
