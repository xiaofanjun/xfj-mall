package com.xfj.shopping.services;

import com.xfj.shopping.IContentService;
import com.xfj.shopping.constant.GlobalConstants;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.converter.ContentConverter;
import com.xfj.shopping.entitys.PanelContent;
import com.xfj.shopping.mapper.PanelContentMapper;
import com.xfj.shopping.rs.NavListRS;
import com.xfj.shopping.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service(group = "${dubbo-group.name}")
public class ContentServiceImpl implements IContentService {

    @Autowired
    PanelContentMapper panelContentMapper;

    @Autowired
    ContentConverter contentConverter;

    @Override
    public NavListRS queryNavList() {
        NavListRS response=new NavListRS();
        try {
            Example exampleContent = new Example(PanelContent.class);
            exampleContent.setOrderByClause("sort_order");
            Example.Criteria criteriaContent = exampleContent.createCriteria();
            criteriaContent.andEqualTo("panelId", GlobalConstants.HEADER_PANEL_ID);
            List<PanelContent> pannelContents = panelContentMapper.selectByExample(exampleContent);
            //添加缓存操作 TODO
            response.setPannelContentDtos(contentConverter.panelContents2Dto(pannelContents));
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("ContentServiceImpl.queryNavList Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
