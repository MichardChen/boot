package com.etc.mainboot.annotation;

import com.etc.mainboot.enums.BusinessType;
import com.etc.mainboot.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 操作日志记录处理
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    /**
     * 请求模块-title
     */
    String title() default "";

    /**
     * 请求具体业务方法
     */
    BusinessType bizType() default BusinessType.OTHER;

    /**
     * 操作人员类型
     */
    OperatorType operateType() default OperatorType.OTHER;

    /**
     * 是否保存请求参数，默认false
     */
    boolean isSaveData() default false;
}
