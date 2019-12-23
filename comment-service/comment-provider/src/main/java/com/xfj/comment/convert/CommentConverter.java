package com.xfj.comment.convert;

import com.xfj.comment.entitys.Comment;
import com.xfj.comment.entitys.CommentReply;
import com.xfj.comment.dto.CommentDto;
import com.xfj.comment.dto.CommentReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author heps
 * @date 2019/8/14 23:54
 */
@Mapper(componentModel = "spring")
public interface CommentConverter {

    @Mappings({})
    CommentDto comment2Dto(Comment comment);

    List<CommentDto> comment2Dto(List<Comment> commentList);

    CommentReplyDto commentReply2Dto(CommentReply commentReply);

    List<CommentReplyDto> commentReply2Dto(List<CommentReply> commentReplyList);
}
