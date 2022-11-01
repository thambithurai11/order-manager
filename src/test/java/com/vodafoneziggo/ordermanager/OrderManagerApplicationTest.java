package com.vodafoneziggo.ordermanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class for {@link OrderManagerApplication}
 *
 * @author Thambi Thurai Chinnadurai
 */
@SpringBootTest
class OrderManagerApplicationTest {

    @Test
    void testMain() {
        String[] args = {"NONE"};
        OrderManagerApplication.main(args);
    }
}
