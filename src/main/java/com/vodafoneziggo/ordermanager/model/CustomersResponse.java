package com.vodafoneziggo.ordermanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model class holds the Api Response data of all customer details
 *
 * @author Thambi Thurai Chinnadurai
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomersResponse {

    @JsonProperty("page")
    private int page;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("data")
    private List<Customer> customers;
}
