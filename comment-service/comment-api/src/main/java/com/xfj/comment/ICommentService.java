package com.xfj.comment;

import com.xfj.comment.rs.*;
import com.xfj.comment.vo.*;

/**
 * @author heps
 * @date 2019/8/11 22:57
 *
 * 商品评价服务接口
 */
public interface ICommentService {

    /**
     * 添加商品评价
     * @param request 请求参数
     * @return 评价结果
     */
    AddCommentRS addComment(AddCommentVO request);

    /**
     * 根据订单详情id查询评价
     * @param request
     * @return
     */
    CommentRS comment(CommentVO request);

    /**
     * 分页查询某个商品的评价
     * @param request
     * @return
     */
    CommentListRS commentList(CommentListVO request);

    /**
     * 查询某个商品的评价总数
     * @param request
     * @return
     */
    TotalCommentRS totalComment(TotalCommentVO request);

    /**
     * 删除评价
     * @param request
     * @return
     */
    DeleteCommentRS deleteComment(DeleteCommentVO request);

    /**
     * 将商品评价置顶
     * @param request
     * @return
     */
    TopCommentRS topComment(TopCommentVO request);

    /**
     * 商品评价审核
     * @param request
     * @return
     */
    AuditCommentRS auditComment(AuditCommentVO request);

    /**
     * 根据商品评价计算综合评分
     * @param request
     * @return
     */
    ItemScoreRS itemScore(ItemScoreVO request);
}
