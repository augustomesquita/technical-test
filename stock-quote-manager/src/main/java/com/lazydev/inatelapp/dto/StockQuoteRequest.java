package com.lazydev.inatelapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
public class StockQuoteRequest {

    @ApiModelProperty(example = "petr3", required = true)
    @NotBlank
    private String id;

    @ApiModelProperty(example = "{\"2019-01-01\": \"10,50\", \"2019-01-02\": \"11.20\", \"2019-01-03\": \"14\"}", required = true)
    @NotEmpty
    private Map<String, String> quotes;
}
