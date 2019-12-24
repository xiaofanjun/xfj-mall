package com.xfj.search.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.search.consts.SearchRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用请求类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
@Data
public class SearchVO extends AbstractRequest {

    private String keyword;
    private Integer currentPage;
    private Integer pageSize;
    private String sort;
    private Integer priceGt;
    private Integer priceLte;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(keyword)){
            throw new ValidateException(
                    SearchRetCode.REQUEST_CHECK_FAILURE.getCode(),
                    SearchRetCode.REQUEST_CHECK_FAILURE.getMsg());
        }
    }


    @Override
    public String toString() {
        return "SearchRequest{" +
                "keyword='" + keyword + '\'' +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
