package com.xfj.shopping.mapper;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.shopping.entitys.Item;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ItemMapper extends BaseDao<Item> {

    List<Item> selectItemFront(@Param("cid") Long cid,
                               @Param("orderCol") String orderCol, @Param("orderDir") String orderDir,
                               @Param("priceGt") Integer priceGt, @Param("priceLte") Integer priceLte);
}