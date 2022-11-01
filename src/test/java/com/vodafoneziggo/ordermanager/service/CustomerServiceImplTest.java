package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.exception.OrderException;
import com.vodafoneziggo.ordermanager.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Test class for {@link CustomerServiceImpl}
 *
 * @author Thambi Thurai Chinnadurai
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {


    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(manyTimes(), requestTo("http://localhost/v1/users")).andExpect(method(HttpMethod.GET)).andRespond(
                withSuccess(
                        "{\"page\":1,\"per_page\":1,\"total\":2,\"total_pages\":12,\"data\":[{\"id\":1,\"email\":\"test@gmail.com\",\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://reqres.in/img/faces/1-image.jpg\"}]}",
                        MediaType.APPLICATION_JSON));

        ReflectionTestUtils.setField(customerService, "endpoint", "http://localhost/v1/users");
        ReflectionTestUtils.setField(customerService, "apiRestTemplate", restTemplate);
    }


    @Test
    public void testRetrieveCustomers() {

        Customer customer = customerService.retrieveCustomers("test@gmail.com");
        assertEquals("test@gmail.com", customer.getEmail());
    }

    @Test
    public void testRetrieveCustomers_UserNotFound() {

        assertThrows(OrderException.class, () -> {
            customerService.retrieveCustomers("nouser@gmail.com");
        });
    }
}
