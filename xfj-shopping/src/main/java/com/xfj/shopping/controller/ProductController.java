package com.xfj.shopping.controller;

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.shopping.IProductService;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.form.PageInfo;
import com.xfj.shopping.form.PageResponse;
import com.xfj.shopping.rs.AllProductRS;
import com.xfj.shopping.rs.ProductDetailRS;
import com.xfj.shopping.rs.RecommendRS;
import com.xfj.shopping.vo.AllProductVO;
import com.xfj.shopping.vo.ProductDetailVO;
import com.xfj.user.annotation.Anoymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping")
@Api(tags = "ProductController", description = "商品控制层")
public class ProductController {

    @Reference(timeout = 3000, group = "${dubbo-group.name}")
    IProductService productService;

    @Anoymous
    @GetMapping("/product/{id}")
    @ApiOperation("查询商品详情")
    @ApiImplicitParam(name = "id", value = "商品ID", paramType = "path", required = true)
    public ResponseData product(@PathVariable long id) {
        ProductDetailVO request = new ProductDetailVO();
        request.setId(id);
        ProductDetailRS response = productService.getProductDetail(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getProductDetailDto());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 返回商品列表
     *
     * @param pageInfo
     * @return
     */
    @Anoymous
    @GetMapping("/goods")
    @ApiOperation("查询商品列表")
    @ApiImplicitParam(name = "pageInfo", value = "分页信息", dataType = "PageInfo", required = true)
    public ResponseData goods(PageInfo pageInfo) {
        AllProductVO request = new AllProductVO();
        request.setCid(pageInfo.getCid());
        request.setPage(pageInfo.getPage());
        request.setPriceGt(pageInfo.getPriceGt());
        request.setPriceLte(pageInfo.getPriceLte());
        request.setSize(pageInfo.getSize());
        request.setSort(pageInfo.getSort());
        AllProductRS response = productService.getAllProduct(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            PageResponse pageResponse = new PageResponse();
            pageResponse.setData(response.getProductDtoList());
            pageResponse.setTotal(response.getTotal());
            return new ResponseUtil().setData(pageResponse);
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 返回推荐的商品
     *
     * @return
     */
    @Anoymous
    @GetMapping("/recommend")
    @ApiOperation("查询推荐的商品")
    public ResponseData recommend() {
        RecommendRS response = productService.getRecommendGoods();
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getPanelContentItemDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

}
