package com.geekbang.coupon.service.impl;

import com.geekbang.coupon.beans.CouponTemplateInfo;
import com.geekbang.coupon.beans.PagedCouponTemplateInfo;
import com.geekbang.coupon.beans.TemplateSearchParams;
import com.geekbang.coupon.converter.CouponTemplateConverter;
import com.geekbang.coupon.enums.CouponType;
import com.geekbang.coupon.service.CouponTemplateService;
import com.geekbang.coupon.repository.CouponTemplateRepository;
import com.geekbang.coupon.repository.entity.CouponTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优惠券模版服务
 *
 * @author wuyuexiang
 * @date 2022年09月12日 22:00
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CouponTemplateServiceImpl implements CouponTemplateService {

    private final CouponTemplateRepository templateRepository;

    @Transactional
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo couponTemplateInfo) {
        // 单个门店最多可以创建100张优惠券模板
        if (couponTemplateInfo.getShopId() != null) {
            Integer count =
                    templateRepository.countByShopIdAndAvailable(couponTemplateInfo.getShopId(), true);
            if (count >= 100) {
                log.error("the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException(
                        "exceeded the maximum of coupon templates that you can create");
            }
        }
        // 创建优惠券
        couponTemplateInfo.setAvailable(true);
        CouponTemplate templateEntity = CouponTemplateConverter.convertBeanToEntity(couponTemplateInfo);
        templateEntity = templateRepository.save(templateEntity);
        return CouponTemplateConverter.convertEntityToBean(templateEntity);
    }

    @Transactional
    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        log.info("cloning template id {}", templateId);
        CouponTemplate source = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("invalid template ID"));

        CouponTemplate target = new CouponTemplate();
        BeanUtils.copyProperties(source, target);
        target.setAvailable(true);
        target.setId(null);
        templateRepository.save(target);
        return CouponTemplateConverter.convertEntityToBean(target);
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams searchParams) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(searchParams.getShopId())
                .type(CouponType.convert(searchParams.getType()))
                .available(searchParams.getAvailable())
                .name(searchParams.getName())
                .build();

        Pageable page = PageRequest.of(searchParams.getPage(), searchParams.getPageSize());
        Page<CouponTemplate> result = templateRepository.findAll(Example.of(example), page);
        List<CouponTemplateInfo> couponTemplateInfos = result.stream()
                .map(CouponTemplateConverter::convertEntityToBean)
                .collect(Collectors.toList());

        return PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(searchParams.getPage())
                .total(result.getTotalElements())
                .build();
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        Optional<CouponTemplate> template = templateRepository.findById(id);
        return template.map(CouponTemplateConverter::convertEntityToBean).orElse(null);
    }

    @Transactional
    @Override
    public void deleteTemplate(Long id) {
        int rows = templateRepository.makeCouponUnavailable(id);
        if (rows == 0) {
            throw new IllegalArgumentException("Template Not Found: " + id);
        }
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> couponTemplates = templateRepository.findAllById(ids);
        return couponTemplates.stream()
                .map(CouponTemplateConverter::convertEntityToBean)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
    }
}
