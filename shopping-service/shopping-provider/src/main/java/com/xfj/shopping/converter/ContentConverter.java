package com.xfj.shopping.converter;

import com.xfj.shopping.entitys.Panel;
import com.xfj.shopping.entitys.PanelContent;
import com.xfj.shopping.entitys.PanelContentItem;
import com.xfj.shopping.dto.PanelContentDto;
import com.xfj.shopping.dto.PanelContentItemDto;
import com.xfj.shopping.dto.PanelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentConverter {

    @Mappings({})
    PanelContentDto panelContent2Dto(PanelContent panelContent);

    @Mappings({})
    PanelContentDto panelContentItem2Dto(PanelContentItem panelContentItem);

    @Mappings({})
    PanelDto panen2Dto(Panel panel);

    List<PanelContentDto> panelContents2Dto(List<PanelContent> panelContents);

    List<PanelContentItemDto> panelContentItem2Dto(List<PanelContentItem> panelContentItems);

}
