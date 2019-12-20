package com.xfj.shopping.mapper;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.shopping.entitys.Panel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PanelMapper extends BaseDao<Panel> {

    List<Panel> selectPanelContentById(@Param("panelId") Integer panelId);
}