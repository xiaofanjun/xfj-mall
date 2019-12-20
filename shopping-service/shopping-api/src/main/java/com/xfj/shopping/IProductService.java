package com.xfj.shopping;

import com.xfj.shopping.rs.AllProductRS;
import com.xfj.shopping.rs.ProductDetailRS;
import com.xfj.shopping.rs.RecommendRS;
import com.xfj.shopping.vo.AllProductVO;
import com.xfj.shopping.vo.ProductDetailVO;

public interface IProductService {

    /**
     * 查看商品明细
     *
     * @param request
     * @return
     */
    ProductDetailRS getProductDetail(ProductDetailVO request);

    /**
     * 查询所有商品（分页）
     *
     * @param request
     * @return
     */
    AllProductRS getAllProduct(AllProductVO request);

    /**
     * 获取推荐的商品板块
     *
     * @return
     */
    RecommendRS getRecommendGoods();

}
