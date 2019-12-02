package com.xfj.commons.base.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @CLassNmae BaseDao
 * @Description
 * @Author ZQ
 * @Date 2019/12/2 14:13
 * @Version 1.0
 **/
public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}
