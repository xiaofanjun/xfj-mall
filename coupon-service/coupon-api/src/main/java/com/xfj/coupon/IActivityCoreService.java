package com.xfj.coupon;

import com.xfj.coupon.vo.CreateActiVO;
import com.xfj.coupon.rs.CreateActiRS;

/**
 * Created by oahnus on 2019/8/16
 * 0:10.
 */
public interface IActivityCoreService {
    // 创建营销活动
    CreateActiRS create(CreateActiVO request);
    // 修改活动
    void update();
    // 删除活动
    void delete();
}
