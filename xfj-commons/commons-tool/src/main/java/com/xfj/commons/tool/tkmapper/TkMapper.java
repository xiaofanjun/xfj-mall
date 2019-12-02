package com.xfj.commons.tool.tkmapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author ZQ
 * @Description 之前的增删改方法
 * @Date 2019/12/1 21:45
 * @Param
 * @return
 **/
@Deprecated
public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
