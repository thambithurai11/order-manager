package com.vodafoneziggo.ordermanager.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Model class that holds the Order Request details
 *
 * @author Thambi Thurai Chinnadurai
 */
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
