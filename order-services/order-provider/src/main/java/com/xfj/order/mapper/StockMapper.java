package com.xfj.order.mapper;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.order.entitys.Stock;

import java.util.List;

public interface StockMapper extends BaseDao<Stock> {
 void updateStock(Stock stock);
 Stock selectStockForUpdate(Long itemId);
 Stock selectStock(Long itemId);
 List<Stock> findStocksForUpdate(List<String> itemIds);
}