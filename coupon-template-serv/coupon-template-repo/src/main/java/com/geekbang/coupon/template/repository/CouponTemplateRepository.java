package com.geekbang.coupon.template.repository;

import com.geekbang.coupon.template.repository.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * @author wuyuexiang
 * @date 2022年09月12日 01:36
 */
public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {

    /**
     * 根据Shop ID查询出所有券模板
     *
     * @param shopId 店铺id
     * @return 所有券模板
     */
    List<CouponTemplate> findAllByShopId(Long shopId);

    /**
     * IN查询 + 分页支持的语法
     *
     * @param ids 模版id
     * @param page 分页接口
     * @return 模版分页
     */
    Page<CouponTemplate> findAllByIdIn(Set<Long> ids, Pageable page);

    /**
     * 根据shop ID + 可用状态查询店铺有多少券模板
     *
     * @param shopId 店铺id
     * @param available 模板是否可用
     * @return 券模板数量
     */
    Integer countByShopIdAndAvailable(Long shopId, Boolean available);

    /**
     * 将优惠券设置为不可用
     *
     * @param id 模版id
     * @return 更新条数
     */
    @Modifying
    @Query("update CouponTemplate c set c.available = 0 where c.id = :id")
    int makeCouponUnavailable(@Param("id") Long id);
}
