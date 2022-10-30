package com.vodafoneziggo.ordermanager.model;

/**
 * OrderDTO
 * DTO Object for request to create order
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    @Positive(message = "Invalid productId")
    private final Long productId;

    @NotEmpty
    @Email(message = "Invalid Email address")
    private final String email;
}
