package com.xfj.shopping.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.shopping.dto.PanelDto;
import lombok.Data;

import java.util.Set;

@Data
public class RecommendRS extends AbstractResponse {

    private Set<PanelDto> panelContentItemDtos;

}
