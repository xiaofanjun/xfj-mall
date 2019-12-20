package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private Date created;

    private Date updated;

    private List<PanelContentItem> panelContentItems;

    @Transient
    private Long productId;

}