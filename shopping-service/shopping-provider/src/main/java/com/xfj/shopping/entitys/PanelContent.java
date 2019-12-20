package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/12/20 19:39
 * @Param
 * @return
 **/
@Data
@Table(name = "tb_panel_content")
public class PanelContent extends BaseDO<String> {

    private String panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

}