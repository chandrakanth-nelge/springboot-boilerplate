package com.chan.nel.springboot.boilerplate.dto;

import java.util.ArrayList;
import java.util.List;
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
@JsonPropertyOrder({ "uuid", "name", "claims" })
@Data
@NoArgsConstructor
@ApiModel(description = "All details about the role.")
public class RoleDto {

	@ApiModelProperty(notes = "The uuid of the role", position = 0)
	private UUID uuid;

	@ApiModelProperty(notes = "The name of the role", position = 1)
	private String name;

	@ApiModelProperty(notes = "List of claims for this role", position = 2)
	private List<ClaimDto> claims = new ArrayList<>();
}