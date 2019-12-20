package com.xfj.shopping.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.shopping.dto.PanelContentDto;
import lombok.Data;

import java.util.List;

@Data
public class NavListRS extends AbstractResponse {

    private List<PanelContentDto> pannelContentDtos;
}
