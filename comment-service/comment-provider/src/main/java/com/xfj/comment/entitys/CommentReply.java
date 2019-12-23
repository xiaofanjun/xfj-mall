package com.xfj.comment.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_comment_reply")
@Data
public class CommentReply extends BaseDO<String> {
    /**
     * 商品评价id
     */
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 评价回复自关联id(针对回复的回复)
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 回复意见
     */
    private String content;

    /**
     * 回复时间
     */
    @Column(name = "reply_time")
    private Date replyTime;

    /**
     * 回复人昵称
     */
    @Column(name = "reply_nick")
    private String replyNick;

    /**
     * 回复人用户id
     */
    @Column(name = "user_id")
    private String userId;

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