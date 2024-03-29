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
@JsonPropertyOrder({ "uuid", "name" })
@Data
@NoArgsConstructor
@ApiModel(description = "The uuid and name of the role with user details.")
public class UserRoleDto {

	@ApiModelProperty(notes = "The uuid of the role", position = 0)
	private UUID uuid;

	@ApiModelProperty(notes = "The name of the role", position = 1)
	private String name;

}