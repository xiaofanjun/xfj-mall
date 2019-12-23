package com.xfj.coupon.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_queue_msg")
@Data
public class QueueMsg extends BaseDO<String> {
    /**
     * 队列名称
     */
    private String queue;

    /**
     * 队列topic
     */
    private String topic;

    /**
     * 队列listener的方法名
     */
    private String method;

    /**
     * 队列listener的方法参数
     */
    private String args;

    /**
     * 方法执行完成的时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

}