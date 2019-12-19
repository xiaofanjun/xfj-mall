package com.xfj.user.services.bl;

import com.xfj.commons.base.service.BaseService;
import com.xfj.commons.tool.utils.ImageUtil;
import com.xfj.user.IUserLoginService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.MemberConverter;
import com.xfj.user.entitys.Member;
import com.xfj.user.entitys.SystemConfig;
import com.xfj.user.mapper.MemberMapper;
import com.xfj.user.mapper.SystemConfigMapper;
import com.xfj.user.rs.CheckAuthRS;
import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.utils.SystemConfigUtil;
import com.xfj.user.vo.CheckAuthVO;
import com.xfj.user.vo.HeadImageVO;
import com.xfj.user.vo.UpdateMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:21
 **/
@Slf4j
@Component
public class MemberServiceBl extends BaseService<Member, String> {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    IUserLoginService userLoginService;

    @Autowired
    MemberConverter memberConverter;
    @Autowired
    SystemConfigMapper systemConfigMapper;

    /**
     * @return com.xfj.user.rs.HeadImageRS
     * @Author ZQ
     * @Description 上传用户头像
     * 1: 上传图片到服务器
     * 2：更新用户表字段
     * 3: 更新token
     * @Date 2019/12/19 13:58
     * @Param [request]
     **/
    @Transactional
    public HeadImageRS updateHeadImage(HeadImageVO request) throws Exception {
        HeadImageRS response = new HeadImageRS();
        String userId = request.getUserId();
        String imageData = request.getImageData();
        //把文件图像文件上传到服务器
        Example example = new Example(SystemConfig.class);
        example.createCriteria().andEqualTo("module", "100").
                orEqualTo("module", "101");
        List<SystemConfig> systemConfigs = systemConfigMapper.selectByExample(example);
        Map<String, Object> initConfig = SystemConfigUtil.getConfigUploadImage(systemConfigs);
        String fileName = new ImageUtil(initConfig).upLoadImageBase64(imageData, userId);
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("上传图片失败");
        }
        //更新用户图片
        Member member = memberMapper.selectByPrimaryKey(userId);
        member.setFile(fileName);
        this.update(member);
        response.setImageUrl(fileName);
        return response;
    }


    @Transactional
    public UpdateMemberRS updateMember(UpdateMemberVO request) {
        UpdateMemberRS response = new UpdateMemberRS();
        CheckAuthVO checkAuthRequest = new CheckAuthVO();
        checkAuthRequest.setToken(request.getToken());
        CheckAuthRS authResponse = userLoginService.validToken(checkAuthRequest);
        if (!authResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            response.setCode(authResponse.getCode());
            response.setMsg(authResponse.getMsg());
            return response;
        }
        Member member = memberConverter.updateReq2Member(request);
        int row = memberMapper.updateByPrimaryKeySelective(member);
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        log.info("MemberServiceImpl.updateMember effect row :" + row);
        return response;
    }
}
