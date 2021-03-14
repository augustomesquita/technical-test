package com.lazydev.inatelapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    @ApiModelProperty(example = "User Test", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "user@test.com", required = true)
    @Email
    @NotBlank
    private String email;
}
