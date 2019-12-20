package com.xfj.shopping;

import com.xfj.shopping.vo.AllProductCateVO;
import com.xfj.shopping.rs.AllProductCateRS;

public interface IProductCateService {
    /**
     * 获取所有产品分类
     *
     * @param request
     * @return
     */
    AllProductCateRS getAllProductCate(AllProductCateVO request);
}
