package com.lazydev.inatelapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class StockQuote {

    @ApiModelProperty(example = "petr3", required = true)
    @NotBlank
    @Id
    private String id;

    @ApiModelProperty(required = true)
    @NotEmpty(message = "Quotes cannot be empty.")
    @OneToMany(mappedBy = "stockQuote", cascade = CascadeType.ALL)
    private List<Quote> quotes;

}
