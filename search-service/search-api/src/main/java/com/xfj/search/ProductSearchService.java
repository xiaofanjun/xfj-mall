package com.xfj.search;

import com.xfj.search.vo.SearchVO;
import com.xfj.search.rs.SearchRS;

/**
 * 商城全部商品搜索API推荐放在此接口中
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public interface ProductSearchService {
    /**
     * 搜索商品 精准搜索
     *
     * @param request request
     * @return SearchResponse
     */
    SearchRS search(SearchVO request);

    /**
     * 搜索商品 模糊搜索
     *
     * @param request request
     * @return SearchResponse
     */
    SearchRS fuzzySearch(SearchVO request);

    /**
     * 商品热门搜索关键字 **热搜推荐**
     *
     * @return SearchResponse
     */
    SearchRS hotProductKeyword();

}
