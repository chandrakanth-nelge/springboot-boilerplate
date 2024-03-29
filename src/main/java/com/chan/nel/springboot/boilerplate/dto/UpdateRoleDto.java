package com.chan.nel.springboot.boilerplate.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chan.nel.springboot.boilerplate.dto.validator.Extended;
import com.chan.nel.springboot.boilerplate.dto.validator.UniqueResource;
import com.chan.nel.springboot.boilerplate.dto.validator.UniqueRoleValidator;
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
@ApiModel(description = "All details about creating the role.")
@UniqueResource(constraintValidator = UniqueRoleValidator.class, groups = Extended.class)
public class UpdateRoleDto {
	@ApiModelProperty(notes = "The uuid of the role", position = 0)
	@NotNull(message = "1013")
	private UUID uuid;

	@ApiModelProperty(notes = "The name of the role", position = 1)
	@NotBlank(message = "1010")
	@Size(max = 50, message = "1011")
	private String name;

	@ApiModelProperty(notes = "List of claims for this role", position = 2)
	@Size(min = 1, message = "1020")
	private List<UUID> claims = new ArrayList<>();
}