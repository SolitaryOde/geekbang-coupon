package com.coupon.client.repository;

import com.coupon.client.repository.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 优惠券数据仓库
 *
 * @author wuyuexiang
 * @date 2022年09月14日 19:24
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * 根据客户和模版查询优惠券数量
     *
     * @param customerId 客户id
     * @param templateId 模版id
     * @return 优惠券数量
     */
    Long countByCustomerAndTemplate(Long customerId, Long templateId);
}
