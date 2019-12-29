package com.xfj.shopping.controller;

import com.xfj.comment.ICommentService;
import com.xfj.comment.rs.CommentListRS;
import com.xfj.comment.rs.TotalCommentRS;
import com.xfj.comment.vo.CommentListVO;
import com.xfj.comment.vo.TotalCommentVO;
import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping/comment")
@Api(tags = "ProductCommentController", description = "商品评价控制层")
public class ProductCommentController {
    @Reference(timeout = 3000, group = "${dubbo-group.name}")
    ICommentService commentService;

    @GetMapping("/all")
    @ApiOperation("获取所有评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query")
    })
    public ResponseData getCommentByItemId(
            @RequestParam("productId") String itemId,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "type", required = false) Integer type
    ) {
        CommentListVO request = new CommentListVO();
        request.setItemId(itemId);
        request.setPage(page);
        request.setSize(size);
        request.setType(type);
        CommentListRS response = commentService.commentList(request);

        return new ResponseUtil<CommentListRS>().setData(response);
    }

    /**
     * TODO type 创建枚举
     *
     * @param itemId
     * @param type
     * @return
     */
    @GetMapping("/count")
    @ApiOperation("获取指定商品的评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query", required = true)
    })
    public ResponseData getCommentCountByItemId(@RequestParam("productId") String itemId,
                                                @RequestParam(value = "type", required = false) Integer type) {
        TotalCommentVO totalCommentRequest = new TotalCommentVO();
        totalCommentRequest.setItemId(itemId);
        totalCommentRequest.setType(type);
        TotalCommentRS response = commentService.totalComment(totalCommentRequest);
        long total = response.getTotal();
        return new ResponseUtil<Long>().setData(total);
    }
}
