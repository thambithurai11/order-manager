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
 * Test class for {@link OrderRequest}
 *
 * @author Thambi Thurai Chinnadurai
 */
@JsonTest
@ExtendWith(SpringExtension.class)
class OrderRequestTest {

    @Autowired
    JacksonTester<OrderRequest> orderRequestTester;

    @Test
    void testSerialize() throws Exception {

        OrderRequest orderRequest = OrderRequest.builder().email("test@gmail.com").productId(10L).build();

        JsonContent<OrderRequest> result = this.orderRequestTester.write(orderRequest);

        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("test@gmail.com");
        assertThat(result).extractingJsonPathNumberValue("$.productId").isEqualTo(10);
    }
}
