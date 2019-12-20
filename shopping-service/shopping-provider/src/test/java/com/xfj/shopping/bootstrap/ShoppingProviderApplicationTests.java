package com.xfj.shopping.bootstrap;

import com.xfj.shopping.vo.AddCartVO;
import com.xfj.shopping.vo.AllProductCateVO;
import com.xfj.shopping.vo.ProductDetailVO;
import com.xfj.shopping.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingProviderApplicationTests {

    @Autowired
    private ICartService cartService;


    @Test
    public void testCartService() throws IOException {
        AddCartVO request = new AddCartVO();
        request.setItemId(100023501L);
        request.setUserId(123L);
        cartService.addToCart(request);
        System.in.read();
    }

    @Autowired
    private IContentService contentService;

    @Test
    public void testContentService() throws IOException {
        contentService.queryNavList();
        System.in.read();
    }

    @Autowired
    private IHomeService homeService;

    @Test
    public void testHomeService() throws IOException {
        homeService.homepage();
        System.in.read();
    }

    @Autowired
    private IProductCateService productCateService;

    @Test
    public void testProductCateService() throws IOException {
        AllProductCateVO request = new AllProductCateVO();
        request.setSort("1");
        productCateService.getAllProductCate(request);
        System.in.read();
    }

    @Autowired
    private IProductService productService;

    @Test
    public void testProductService() throws IOException {
        ProductDetailVO productDetailRequest = new ProductDetailVO();
        productDetailRequest.setId(100023501L);
        productService.getProductDetail(productDetailRequest);

        // ----------------------------------------------------------------------------

//        productService.getRecommendGoods();

        System.in.read();
    }
}
