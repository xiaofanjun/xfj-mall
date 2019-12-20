package com.xfj.shopping.mapper;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.shopping.entitys.PanelContent;

import java.util.List;

import com.xfj.shopping.entitys.PanelContentItem;
import org.apache.ibatis.annotations.Param;

public interface PanelContentMapper extends BaseDao<PanelContent> {

    List<PanelContentItem> selectPanelContentAndProductWithPanelId(@Param("panelId") String panelId);
}