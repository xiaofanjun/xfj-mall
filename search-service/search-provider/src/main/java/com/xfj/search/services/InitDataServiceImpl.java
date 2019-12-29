package com.xfj.search.services;

import com.xfj.search.InitDataService;
import com.xfj.search.converter.ProductConverter;
import com.xfj.search.entitys.Item;
import com.xfj.search.mapper.ItemMapper;
import com.xfj.search.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Service(group = "${dubbo-group.name}")
public class InitDataServiceImpl implements InitDataService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ProductConverter productConverter;

    @Override
    public void initItems() {
        List<Item> items = itemMapper.selectAll();
        items.parallelStream().forEach(item -> {
            productRepository.save(productConverter.item2Document(item));
        });
    }
}
