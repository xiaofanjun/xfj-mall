package com.xfj.search.rs;


import com.xfj.commons.result.AbstractResponse;
import com.xfj.search.consts.SearchRetCode;
import lombok.Data;

import java.util.List;

/**
 * 通用响应类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
@Data
public class SearchRS<T> extends AbstractResponse {
     private Long total;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public SearchRS setData(List<T> data) {
        this.data = data;
        return this;
    }

    public SearchRS ok(List<T> data) {
        this.setCode(SearchRetCode.SUCCESS.getCode());
        this.setMsg(SearchRetCode.SUCCESS.getMsg());
        this.setTotal(total);
        this.setData(data);
        return this;
    }
}
