package com.xfj.shopping.services;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfj.shopping.IProductService;
import com.xfj.shopping.constant.GlobalConstants;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.converter.ContentConverter;
import com.xfj.shopping.converter.ProductConverter;
import com.xfj.shopping.mapper.ItemDescMapper;
import com.xfj.shopping.mapper.ItemMapper;
import com.xfj.shopping.mapper.PanelContentMapper;
import com.xfj.shopping.mapper.PanelMapper;
import com.xfj.shopping.rs.AllProductRS;
import com.xfj.shopping.rs.ProductDetailRS;
import com.xfj.shopping.rs.RecommendRS;
import com.xfj.shopping.services.cache.CacheManager;
import com.xfj.shopping.utils.ExceptionProcessorUtils;
import com.xfj.shopping.entitys.Item;
import com.xfj.shopping.entitys.ItemDesc;
import com.xfj.shopping.entitys.Panel;
import com.xfj.shopping.entitys.PanelContentItem;
import com.xfj.shopping.dto.*;
import com.xfj.shopping.vo.AllProductVO;
import com.xfj.shopping.vo.ProductDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
@Service(group = "${dubbo-group.name}")
public class ProductServiceImpl implements IProductService {
    @Autowired
    CacheManager cacheManager;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ItemDescMapper itemDescMapper;
    @Autowired
    ProductConverter productConverter;
    @Autowired
    PanelMapper panelMapper;

    @Autowired
    PanelContentMapper panelContentMapper;
    @Autowired
    ContentConverter contentConverter;

    @Override
    public ProductDetailRS getProductDetail(ProductDetailVO request) {
        ProductDetailRS response = new ProductDetailRS();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {
            //查询缓存
            String json = cacheManager.checkCache(generatorProduceCacheKey(request));
            if (StringUtils.isNotBlank(json)) {
                ProductDetailDto productDetailDto = JSON.parseObject(json, ProductDetailDto.class);
                cacheManager.expire(generatorProduceCacheKey(request), GlobalConstants.PRODUCT_ITEM_EXPIRE_TIME);
                response.setProductDetailDto(productDetailDto);
                return response;
            }
            Item item = itemMapper.selectByPrimaryKey(request.getId().longValue());
            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductId(request.getId().longValue());
            productDetailDto.setProductName(item.getTitle());
            productDetailDto.setSubTitle(item.getSellPoint());
            productDetailDto.setLimitNum(item.getLimitNum() == null ? item.getNum().longValue() : item.getLimitNum().longValue());
            productDetailDto.setSalePrice(item.getPrice());

            ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(request.getId().longValue());
            productDetailDto.setDetail(itemDesc.getItemDesc());
            if (StringUtils.isNotBlank(item.getImage())) {
                String images[] = item.getImage().split(",");
                productDetailDto.setProductImageBig(images[0]);
                productDetailDto.setProductImageSmall(Arrays.asList(images));
            }
            response.setProductDetailDto(productDetailDto);
            //设置缓存
            cacheManager.setCache(generatorProduceCacheKey(request), JSON.toJSON(productDetailDto).toString(), GlobalConstants.PRODUCT_ITEM_EXPIRE_TIME);
        } catch (Exception e) {
            log.error("ProductServiceImpl.getProductDetail Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public AllProductRS getAllProduct(AllProductVO request) {
        AllProductRS response = new AllProductRS();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {
            List<ProductDto> productDtos = new ArrayList<>();
            PageHelper.startPage(request.getPage(), request.getSize());
            String orderCol = "created";
            String orderDir = "desc";
            if (request.getSort().equals("1")) {
                orderCol = "price";
                orderDir = "asc";
            } else if (request.getSort().equals("-1")) {
                orderCol = "price";
                orderDir = "desc";
            }
            List<Item> items = itemMapper.selectItemFront(request.getCid(), orderCol, orderDir, request.getPriceGt(), request.getPriceLte());
            PageInfo<Item> pageInfo = new PageInfo<>(items);
            List<ProductDto> productDtosList = productConverter.items2Dto(items);
            response.setProductDtoList(productDtosList);
            response.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.error("ProductServiceImpl.getAllProduct Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public RecommendRS getRecommendGoods() {
        RecommendRS response = new RecommendRS();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {

            String json = cacheManager.checkCache(GlobalConstants.RECOMMEND_PANEL_CACHE_KEY);
            if (StringUtils.isNotBlank(json)) {
                List<PanelDto> panelContentItemDtoList = JSON.parseArray(json, PanelDto.class);
                Set<PanelDto> panelDtoSet = new HashSet<>(panelContentItemDtoList);
                response.setPanelContentItemDtos(panelDtoSet);
                return response;
            }
            List<Panel> panels = panelMapper.selectPanelContentById(GlobalConstants.RECOMMEND_PANEL_ID);

            System.out.println(panels);
            if (panels == null || panels.isEmpty()) {
                return response;
            }
            Set<PanelDto> panelContentItemDtos = new HashSet<PanelDto>();
            panels.parallelStream().forEach(panel -> {
                List<PanelContentItem> panelContentItems = panelContentMapper.selectPanelContentAndProductWithPanelId(panel.getId());
                PanelDto panelDto = contentConverter.panen2Dto(panel);
                panelDto.setPanelContentItems(contentConverter.panelContentItem2Dto(panelContentItems));
                panelContentItemDtos.add(panelDto);
            });
/*
            panels.parallelStream().forEach(panel -> {
                RecommendDto recommendDto=new RecommendDto();
                Item item=itemMapper.selectByPrimaryKey(panel.getProductId());
                recommendDto.setId(item.getId().intValue());
                recommendDto.setName(item.getTitle());
                recommendDto.setPosition(panel.getPosition());
                recommendDto.setSortOrder(panel.getSortOrder());
                recommendDto.setType(panel.getType());
                recommendDtos.add(recommendDto);
            });*/
            response.setPanelContentItemDtos(panelContentItemDtos);
            cacheManager.setCache(GlobalConstants.RECOMMEND_PANEL_CACHE_KEY, JSON.toJSONString(panelContentItemDtos), GlobalConstants.RECOMMEND_CACHE_EXPIRE);

        } catch (Exception e) {
            log.error("ProductServiceImpl.getAllProduct Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    private String generatorProduceCacheKey(ProductDetailVO request) {
        StringBuilder stringBuilder = new StringBuilder(GlobalConstants.PRODUCT_ITEM_CACHE_KEY);
        stringBuilder.append(":").append(request.getId());
        return stringBuilder.toString();
    }


}
