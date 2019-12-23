package com.xfj.comment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfj.comment.CommentException;
import com.xfj.comment.ICommentReplyService;
import com.xfj.comment.constant.CommentRetCode;
import com.xfj.comment.convert.CommentConverter;
import com.xfj.comment.entitys.Comment;
import com.xfj.comment.entitys.CommentReply;
import com.xfj.comment.mapper.CommentMapper;
import com.xfj.comment.mapper.CommentReplyMapper;
import com.xfj.comment.rs.AddCommentReplyRS;
import com.xfj.comment.rs.CommentReplyListRS;
import com.xfj.comment.rs.DeleteCommentReplyRS;
import com.xfj.comment.utils.ExceptionProcessorUtil;
import com.xfj.comment.utils.GlobalIdGeneratorUtil;
import com.xfj.comment.vo.AddCommentReplyVO;
import com.xfj.comment.vo.CommentReplyListVO;
import com.xfj.comment.vo.DeleteCommentReplyVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author heps
 * @date 2019/8/21 22:55
 * 商品评价回复服务实现
 */
@Service
public class CommentReplyServiceImpl implements ICommentReplyService {

    private final CommentReplyMapper commentReplyMapper;

    private final CommentMapper commentMapper;

    private final CommentConverter commentConverter;

    private final GlobalIdGeneratorUtil globalIdGeneratorUtil;

    private static final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_REPLY_ID";

    public CommentReplyServiceImpl(CommentReplyMapper commentReplyMapper, CommentMapper commentMapper, CommentConverter commentConverter, GlobalIdGeneratorUtil globalIdGeneratorUtil) {
        this.commentReplyMapper = commentReplyMapper;
        this.commentMapper = commentMapper;
        this.commentConverter = commentConverter;
        this.globalIdGeneratorUtil = globalIdGeneratorUtil;
    }

    @Override
    public AddCommentReplyRS addCommentReply(AddCommentReplyVO request) {
        AddCommentReplyRS response = new AddCommentReplyRS();
        try {
            request.requestCheck();
            CommentReply commentReply = new CommentReply();
            String commentId = request.getCommentId();
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            if (comment != null && (comment.getIsDeleted() == null || !comment.getIsDeleted())) {
                commentReply.setCommentId(commentId);
            } else {
                CommentReply reply = commentReplyMapper.selectByPrimaryKey(commentId);
                if (reply != null) {
                    commentReply.setParentId(commentId);
                } else {
                    throw new CommentException(CommentRetCode.ORIGIN_COMMENT_NOT_EXIST.getCode(), CommentRetCode.ORIGIN_COMMENT_NOT_EXIST.getMessage());
                }
            }
            commentReply.setId(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
            commentReply.setContent(request.getContent());
            commentReply.setReplyTime(new Date());
            commentReply.setReplyNick(request.getReplyNick());
            commentReply.setUserId(request.getUserId());
            commentReply.setIsDeleted(false);
            commentReplyMapper.insert(commentReply);

            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public DeleteCommentReplyRS deleteCommentReply(DeleteCommentReplyVO request) {
        DeleteCommentReplyRS response = new DeleteCommentReplyRS();
        try {
            request.requestCheck();
            CommentReply commentReply = commentReplyMapper.selectByPrimaryKey(request.getCommentReplyId());
            if (commentReply == null || (commentReply.getIsDeleted() != null && commentReply.getIsDeleted())) {
                throw new CommentException(CommentRetCode.CURRENT_COMMENT_REPLY_NOT_EXIST.getCode(), CommentRetCode.CURRENT_COMMENT_REPLY_NOT_EXIST.getMessage());
            }
            commentReply.setIsDeleted(true);
            commentReply.setDeletionUserId(request.getUserId());
            commentReply.setDeletionTime(new Date());
            commentReplyMapper.updateByPrimaryKey(commentReply);

            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public CommentReplyListRS commentReplyList(CommentReplyListVO request) {
        CommentReplyListRS response = new CommentReplyListRS();
        try {
            request.requestCheck();

            Example example = new Example(CommentReply.class);
            Example.Criteria criteria = example.createCriteria();
            CommentReply commentReply = commentReplyMapper.selectByPrimaryKey(request.getCommentId());
            if (commentReply != null) {
                criteria.andEqualTo("parentId", request.getCommentId());
            } else {
                criteria.andEqualTo("commentId", request.getCommentId());
            }
            PageHelper.startPage(request.getPage(), request.getSize());
            List<CommentReply> commentReplyList = commentReplyMapper.selectByExample(example);
            PageInfo<CommentReply> pageInfo = new PageInfo<>(commentReplyList);
            if (CollectionUtils.isEmpty(commentReplyList)) {
                response.setCommentReplyDtoList(new ArrayList<>());
            } else {
                response.setCommentReplyDtoList(commentConverter.commentReply2Dto(commentReplyList));
            }
            response.setTotal(pageInfo.getTotal());
            response.setPage(request.getPage());
            response.setSize(request.getSize());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }
}
