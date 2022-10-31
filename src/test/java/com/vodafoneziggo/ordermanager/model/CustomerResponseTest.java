package com.vodafoneziggo.ordermanager.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ExtendWith(SpringExtension.class)
class CustomerResponseTest {

    @Autowired
    JacksonTester<CustomersResponse> customersResponseTester;

    @Test
    void testSerialize() throws Exception {

        CustomersResponse customersResponse = CustomersResponse.builder().page(1).totalPages(1).perPage(5).customers(
                Arrays.asList(new Customer())).build();

        JsonContent<CustomersResponse> result = this.customersResponseTester.write(customersResponse);

        assertThat(result).hasJsonPathNumberValue("$.page");
        assertThat(result).hasJsonPathNumberValue("$.per_page");
        assertThat(result).hasJsonPathNumberValue("$.total_pages");
    }
}
