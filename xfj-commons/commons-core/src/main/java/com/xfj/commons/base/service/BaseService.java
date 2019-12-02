package com.xfj.commons.base.service;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.commons.base.domain.BaseDO;
import com.xfj.commons.utils.SysConstant;
import com.xfj.commons.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @CLassNmae BaseService
 * @Description 基础Service T：实体的子类 PK：主键
 * @Author ZQ
 **/
public class BaseService<T extends BaseDO<PK>, PK> {
    @Autowired
    private BaseDao<T> baseDao;

    /**
     * @return java.lang.String
     * @Author ZQ
     * @Description 自动生成ID
     * @Date 2019/12/1 21:11
     * @Param []
     **/
    protected String generateUUID() {
        return UUIDGenerator.uuid();
    }

    /**
     * @return int
     * @Author ZQ
     * @Description 保存
     * <p>
     * 这里sass_id 先设置成一个默认值
     * <p>
     * operator 前端不需要，等到做后端的时候再搞这个
     * @Date 2019/12/1 21:11
     * @Param [t]
     **/
    public int save(T t) {
        Assert.notNull(t, "待保存数据不可为空");
        if (t.getId() == null || "".equals(t.getId())) {
            ParameterizedType pt = (ParameterizedType) t.getClass().getGenericSuperclass();
            Type[] cs = pt.getActualTypeArguments();
            Class<PK> pkClass = (Class) cs[0];
            if (String.class.isAssignableFrom(pkClass)) {
                t.setId((PK) this.generateUUID());
            }
        }
        if (t.getCreatedate() == null) {
            t.setCreatedate(new Date());
            t.setModifydate(t.getCreatedate());
        }
        t.setSass_id(SysConstant.SASS_ID);
        return baseDao.insert(t);
    }

    /**
     * @return int
     * @Author ZQ
     * @Description 更新操作
     * @Date 2019/12/2 17:30
     * @Param [t]
     **/
    public int update(T t) {
        Assert.notNull(t, "待修改数据不可为空");
        Assert.notNull(t.getId(), "待修改数据主键可不为空");
        t.setModifydate(new Date());
        return this.baseDao.updateByPrimaryKey(t);
    }

    public int updateByExample(T t, Example example) {
        Assert.notNull(t, "待修改数据不可为空");
        Assert.notNull(t.getId(), "待修改数据主键可不为空");
        t.setModifydate(new Date());
        return this.baseDao.updateByExample(t, example);
    }

    /**
     * @return int
     * @Author ZQ
     * @Description 通过主键删除
     * @Date 2019/12/2 17:31
     * @Param [id]
     **/
    public int delete(PK id) {
        Assert.notNull(id, "待删除数据主键不可为空");
        return this.baseDao.deleteByPrimaryKey(id);
    }

    /**
     * @return T
     * @Author ZQ
     * @Description 通过主键查询
     * @Date 2019/12/2 17:34
     * @Param [id]
     **/
    public T get(PK id) {
        Assert.notNull(id, "待查询数据主键不可为空");
        return this.baseDao.selectByPrimaryKey(id);
    }

}
