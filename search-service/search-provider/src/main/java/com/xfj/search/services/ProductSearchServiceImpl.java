package com.xfj.search.services;


import com.google.common.collect.Lists;
import com.xfj.search.ProductSearchService;
import com.xfj.search.constant.PageInfo;
import com.xfj.search.constant.SearchConstants;
import com.xfj.search.converter.ProductConverter;
import com.xfj.search.dto.ProductDto;
import com.xfj.search.vo.SearchVO;
import com.xfj.search.rs.SearchRS;
import com.xfj.search.entitys.ItemDocument;
import com.xfj.search.repository.ProductRepository;
import com.xfj.search.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 商品搜索服务类 对商品搜索接口API进行实现
 * 并对外提供商品搜索服务
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
@Slf4j
@Service(group = "${dubbo-group.name}")
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductConverter productConverter;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public SearchRS search(SearchVO request) {

        SearchRS response = new SearchRS();
        try {
            request.requestCheck();
            //统计搜索热词
            staticsSearchHotWord(request);
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", request.getKeyword()));
            if (request.getPriceGt() != null) {
                boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(request.getPriceGt()));
            }
            if (request.getPriceLte() != null) {
                boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(request.getPriceLte()));
            }
            Sort sort = null;
            if ("1".equals(request.getSort())) {
                sort = new Sort(Sort.Direction.ASC, "price");
            } else if ("-1".equals(request.getSort())) {
                sort = new Sort(Sort.Direction.DESC, "price");
            }
            Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize());
            if (sort != null) {
                pageable = new PageRequest(request.getCurrentPage() - 1, pageable.getPageSize(), sort);
            }
            Iterable<ItemDocument> elasticRes =
                    productRepository.search(boolQueryBuilder, pageable);
            ArrayList<ItemDocument> itemDocuments = Lists.newArrayList(elasticRes);

            List<ProductDto> productDtos = productConverter.items2Dto(itemDocuments);
            response.ok(productDtos);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductSearchServiceImpl.search Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }


    @Override
    public SearchRS fuzzySearch(SearchVO request) {
        SearchRS response = new SearchRS();
        try {
            request.requestCheck();
            //统计搜索热词
            staticsSearchHotWord(request);
            // 分页
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageNumber(request.getCurrentPage());
            pageInfo.setPageSize(request.getPageSize());
            pageInfo.setSort(new Sort(Sort.Direction.DESC, request.getSort()));
            Page<ItemDocument> elasticRes =
                    productRepository.search(QueryBuilders.matchQuery("title", request.getKeyword()), pageInfo);
            ArrayList<ItemDocument> itemDocuments = Lists.newArrayList(elasticRes);
            List<ProductDto> productDtos = productConverter.items2Dto(itemDocuments);
            response.setTotal(elasticRes.getTotalElements());
            response.ok(productDtos);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductSearchServiceImpl.fuzzySearch Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 统计搜索热词
     *
     * @param request request
     */
    private void staticsSearchHotWord(SearchVO request) {
        //搜索词
        String keyword = request.getKeyword();
        if (StringUtils.isNotEmpty(keyword)) {
            RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(getSearchHotWordRedisKey());
            Double score = scoredSortedSet.getScore(keyword);
            if (score != null) {
                scoredSortedSet.addAndGetRank(score + 1.0, keyword);
            } else {
                scoredSortedSet.addScore(keyword, 1);

            }

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public SearchRS hotProductKeyword() {
        //商品热门搜索关键字
        //获取到分数第一的 搜索词
        SearchRS response = new SearchRS();
        try {
            RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(getSearchHotWordRedisKey());
            Object first = scoredSortedSet.first();
            if (!Objects.isNull(first)) {
                response.ok(Collections.singletonList(first));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ProductSearchServiceImpl.hotProductKeyword Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }

        return response;
    }

    private String getSearchHotWordRedisKey() {
        return SearchConstants.SEARCH_HOT_WORD_CACHE_KEY;
    }
}
