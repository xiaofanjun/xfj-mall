package com.xfj.search.repository;

import com.xfj.search.entitys.ItemDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jin
 */
public interface ProductRepository extends ElasticsearchRepository<ItemDocument, Integer> {
}
