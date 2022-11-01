package com.vodafoneziggo.ordermanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model class holds Api customer details
 *
 * @author Thambi Thurai Chinnadurai
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
}
