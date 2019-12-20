package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Author ZQ
 * @Description 内容分类
 * @Date 2019/12/20 19:37
 * @Param
 * @return
 **/
@Data
@Table(name = "tb_panel")
public class Panel extends BaseDO<String> {
    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private Integer status;

    private String remark;


    private List<PanelContentItem> panelContentItems;

    @Transient
    private String productId;

}