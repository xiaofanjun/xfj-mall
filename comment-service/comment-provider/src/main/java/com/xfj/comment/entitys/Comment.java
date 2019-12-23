package com.xfj.comment.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_comment")
@Data
public class Comment extends BaseDO<String> {

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private String itemId;

    /**
     * 星级
     */
    private Byte star;

    /**
     * 类型: 1好评 2中评 3差评
     */
    private Byte type;

    /**
     * 是否匿名评价
     */
    @Column(name = "is_anoymous")
    private Boolean isAnoymous;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 买家昵称
     */
    @Column(name = "buyer_nick")
    private String buyerNick;

    /**
     * 评价时间
     */
    @Column(name = "comment_time")
    private Date commentTime;

    /**
     * 是否公开
     */
    @Column(name = "is_public")
    private Boolean isPublic;

    /**
     * 是否通过审核
     */
    @Column(name = "is_valid")
    private Boolean isValid;

    /**
     * 审核人id
     */
    @Column(name = "validation_user_id")
    private Long validationUserId;

    /**
     * 审核时间
     */
    @Column(name = "validation_time")
    private Date validationTime;

    /**
     * 审核意见
     */
    @Column(name = "validation_suggestion")
    private String validationSuggestion;

    /**
     * 是否置顶
     */
    @Column(name = "is_top")
    private Boolean isTop;

    /**
     * 评论用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 删除时间
     */
    @Column(name = "deletion_time")
    private Date deletionTime;

    /**
     * 删除用户id
     */
    @Column(name = "deletion_user_id")
    private String deletionUserId;

}