package com.hezhu.octopusaiagent.common;

import com.hezhu.octopusaiagent.constant.CommonConstant;
import lombok.Data;

/**
 * @Author Marshall
 * @Date 2025/7/13 16:02
 * @Description:
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
