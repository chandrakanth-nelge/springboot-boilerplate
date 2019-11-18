package com.chan.nel.springboot.boilerplate.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uuid", "resourceName" })
@Data
@NoArgsConstructor
@ApiModel(description = "All details about the claims.")
public class ClaimDto {
	@ApiModelProperty(notes = "The uuid of the claim", position = 0)
	private UUID uuid;

	@ApiModelProperty(notes = "The resource name of the claim", position = 1)
	private String resourceName;

}