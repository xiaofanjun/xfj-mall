package com.xfj.comment.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @author heps
 * @date 2019/8/17 23:16
 */
@Data
public class TotalCommentRS extends AbstractResponse {

    private long total;
}
