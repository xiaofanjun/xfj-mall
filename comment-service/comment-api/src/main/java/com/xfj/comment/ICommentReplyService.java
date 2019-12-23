package com.xfj.comment;

import com.xfj.comment.rs.AddCommentReplyRS;
import com.xfj.comment.rs.CommentReplyListRS;
import com.xfj.comment.rs.DeleteCommentReplyRS;
import com.xfj.comment.vo.AddCommentReplyVO;
import com.xfj.comment.vo.CommentReplyListVO;
import com.xfj.comment.vo.DeleteCommentReplyVO;

/**
 * @author hepengshuai
 * @date 2019/8/21 20:46
 * 商品评价回复服务接口
 */
public interface ICommentReplyService {

    /**
     * 新增商品评价回复
     * @param request
     * @return
     */
    AddCommentReplyRS addCommentReply(AddCommentReplyVO request);

    /**
     * 删除商品评价回复
     * @param request
     * @return
     */
    DeleteCommentReplyRS deleteCommentReply(DeleteCommentReplyVO request);

    /**
     * 分页查询商品评价回复意见
     * @param request
     * @return
     */
    CommentReplyListRS commentReplyList(CommentReplyListVO request);
}
