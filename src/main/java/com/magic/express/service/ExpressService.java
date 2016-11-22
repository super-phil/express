package com.magic.express.service;

import com.google.common.collect.Lists;
import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import com.magic.express.repository.ExpressDao;
import com.magic.express.repository.ExpressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private ExpressRepository expressRepository;
    @Resource
    private ExpressDao expressDao;
    public void save(Express express) {
        expressRepository.save(express);
    }
    
    public void save(List<Express> express) {
        expressRepository.save(express);
    }
    
    /**
     * 根据快递号分页
     *
     * @param dtRequest 分页参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public DTResponse<Express> findAllWhereNumberLike(DTRequest dtRequest) {
        final String q="%"+dtRequest.getQ()+"%";
        List<Sort.Order> orders=Lists.newArrayList();
        orders.add(new Sort.Order(Sort.Direction.DESC, "type"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "price"));
        PageRequest pageRequest=new PageRequest(dtRequest.getStart()/dtRequest.getLength(), dtRequest.getLength(), new Sort(orders));
        Specification<Express> specification=new Specification<Express>() {
            @Override
            public Predicate toPredicate(Root<Express> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaQuery.where(criteriaBuilder.like(root.get("number").as(String.class), q)).getRestriction();
            }
        };
        Page<Express> page=expressRepository.findAll(specification, pageRequest);
        DTResponse<Express> response=new DTResponse<Express>();
        response.setRecordsTotal(page.getTotalElements());
        response.setRecordsFiltered(page.getTotalElements());
        response.setDraw(dtRequest.getDraw());
        response.setData(page.getContent());
        return response;
    }
    
    public List<Map<String,Object>> chartsPrice(int days) {
        return expressDao.chartsPrice(days);
    }
    
    
}
