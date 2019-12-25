package com.xfj.shopping.controller;

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.search.InitDataService;
import com.xfj.search.ProductSearchService;
import com.xfj.search.rs.SearchRS;
import com.xfj.search.vo.SearchVO;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.form.SearchPageInfo;
import com.xfj.user.annotation.Anoymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;


/**
 * 商城全部商品搜索和热门推荐
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月11日
 */
@RestController
@RequestMapping("/shopping")
@Api(tags = "SearchController", description = "搜索控制层")
public class SearchController {
    @Reference(timeout = 3000)
    private ProductSearchService productSearchService;

    @Reference(timeout = 3000)
    private InitDataService initDataService;

    @PostMapping("/search")
    @ApiOperation("搜索商品")
    @ApiImplicitParam(name = "pageInfo", value = "搜索入参", dataType = "SearchPageInfo")
    public ResponseData<SearchRS> searchProduct(@RequestBody SearchPageInfo pageInfo) {
        SearchVO request = new SearchVO();
        request.setKeyword(pageInfo.getKey());
        request.setCurrentPage(pageInfo.getPage());
        request.setPageSize(pageInfo.getSize());
        request.setPriceGt(pageInfo.getPriceGt());
        request.setPriceLte(pageInfo.getPriceLte());
        request.setSort(pageInfo.getSort());
        SearchRS response = productSearchService.search(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getData());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @Anoymous
    @GetMapping("/searchHotWord")
    @ApiOperation("搜索热词")
    public ResponseData<SearchRS> getSearchHotWord() {
        SearchRS searchResponse = productSearchService.hotProductKeyword();
        if (searchResponse.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(searchResponse.getData());
        }
        return new ResponseUtil().setErrorMsg(searchResponse.getMsg());
    }

    @Anoymous
    @GetMapping("/search/{key}")
    public ResponseData search(@PathVariable("key")String key){
        SearchVO searchRequest=new SearchVO();
        searchRequest.setKeyword(key);
        SearchRS searchResponse=productSearchService.fuzzySearch(searchRequest);
        if (searchResponse.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(searchResponse.getData());
        }
        return new ResponseUtil().setErrorMsg(searchResponse.getMsg());
    }

    @Anoymous
    @GetMapping("/search/init")
    public ResponseData init(){
        initDataService.initItems();
        return new ResponseUtil().setData(null);
    }
}
