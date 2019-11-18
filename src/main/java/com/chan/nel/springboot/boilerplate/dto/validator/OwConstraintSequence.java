package com.chan.nel.springboot.boilerplate.dto.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@GroupSequence({ Default.class, Extended.class })
public interface OwConstraintSequence {

}