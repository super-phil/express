package com.magic.express.service;

import com.magic.express.exception.BusinessException;
import com.magic.express.model.Remark;
import com.magic.express.repository.RemarkDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoXiuFei on 2016/12/10.
 */
@Service
public class RemarkService {
    @Resource
    private RemarkDao remarkDao;

    public void save(Remark remark) throws BusinessException {
        remarkDao.save(remark);
    }

    public void del(String id) throws BusinessException {
        remarkDao.del(id);
    }

    public List<Map<String, Object>> list() throws BusinessException {
        return remarkDao.list();
    }
}
