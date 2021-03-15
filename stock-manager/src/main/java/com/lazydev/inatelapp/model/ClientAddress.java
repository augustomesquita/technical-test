package com.lazydev.inatelapp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ClientAddress {

    @ApiModelProperty(example = "localhost", required = true)
    @NotBlank
    private String host;

    @ApiModelProperty(example = "8081", required = true)
    @NotNull
    @Range(min = 1)
    private Integer port;

}
