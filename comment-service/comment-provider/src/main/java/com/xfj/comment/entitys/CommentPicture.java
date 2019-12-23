package com.xfj.comment.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.*;

@Table(name = "tb_comment_picture")
@Data
public class CommentPicture extends BaseDO<String> {
    /**
     * 商品评价id
     */
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 图片路径
     */
    @Column(name = "pic_path")
    private String picPath;

}