package com.vodafoneziggo.ordermanager.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link Customer}
 *
 * @author Thambi Thurai Chinnadurai
 */
@JsonTest
@ExtendWith(SpringExtension.class)
class CustomerTest {

    @Autowired
    JacksonTester<Customer> errorResponseTester;

    @Test
    void testSerialize() throws Exception {

        Customer errorResponse = Customer.builder().email("test@gmail.com").firstName("Test").lastName("User").build();

        JsonContent<Customer> result = this.errorResponseTester.write(errorResponse);

        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("test@gmail.com");
        assertThat(result).extractingJsonPathStringValue("$.first_name").isEqualTo("Test");
        assertThat(result).extractingJsonPathStringValue("$.last_name").isEqualTo("User");
    }
}
