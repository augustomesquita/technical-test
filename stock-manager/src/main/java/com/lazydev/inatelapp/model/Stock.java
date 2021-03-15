package com.lazydev.inatelapp.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Stock {

    @ApiModelProperty(example = "petr3", required = true)
    @NotBlank
    private String id;
    private String description;

}
