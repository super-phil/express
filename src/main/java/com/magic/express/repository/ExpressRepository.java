package com.magic.express.repository;

import com.magic.express.model.Express;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/16 12:24
 *          ExpressRepository
 */
public interface ExpressRepository extends PagingAndSortingRepository<Express,Long>, JpaSpecificationExecutor {
}
