package com.coupon.client.repository.entity;

import com.coupon.client.repository.converter.CouponStatusConverter;
import com.geekbang.coupon.client.api.enumes.CouponStatus;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 优惠券实体
 *
 * @author wuyuexiang
 * @date 2022年09月14日 19:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 对应的模板ID - 不使用one to one映射
    // 不推荐使用级联查询的原因是为了防止滥用而导致的DB性能问题
    @Column(name = "template_id", nullable = false)
    private Long templateId;

    // 所有者的用户ID
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    // 冗余一个shop id方便查找
    @Column(name = "shop_id")
    private Long shopId;

    // 优惠券的使用/未使用状态
    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    // 被Transient标记的属性是不会被持久化的
    @Transient
    private CouponTemplateInfo templateInfo;

    // 获取时间自动生成
    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;
}
