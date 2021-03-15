package com.lazydev.inatelapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
public class StockRequest {

    @ApiModelProperty(example = "petr3", required = true)
    @NotBlank
    private String id;

    @ApiModelProperty(example = "test petr")
    private String description;
}
