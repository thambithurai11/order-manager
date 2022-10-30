package com.vodafoneziggo.ordermanager.webserviceClient;

import com.vodafoneziggo.ordermanager.exception.OrderException;
import com.vodafoneziggo.ordermanager.model.Customer;
import com.vodafoneziggo.ordermanager.model.CustomersResponse;
import com.vodafoneziggo.ordermanager.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CustomersResponse response;

    @Mock
    private Customer customers;


    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(customerService, "endpoint", "https://testapi.com");
        ReflectionTestUtils.setField(customerService, "perPage", 5);
    }

    @Test
    public void test_RetrieveCustomerDetailsFromAPI() {

        final CustomersResponse apiResponse = CustomersResponse.builder()
                .customers(Collections.singletonList(buildCustomerData())).build();

        Mockito.when(restTemplate.getForObject(eq("https://testapi.com"), eq(CustomersResponse.class)
                , Mockito.argThat(new ArgumentMatcher<Map<String, String>>() {
                    @Override
                    public boolean matches(Map<String, String> params) {
                        return true;

                    }
                }))).thenReturn(apiResponse);

        Customer customer = customerService.retrieveCustomers("test@gmail.com");
        assertEquals(customer.getEmail(), buildCustomerData().getEmail());
    }

    @Test
    public void test_RetrieveCustomerDetailsFromAPI_EMAIL_NOT_FOUND() {

        final CustomersResponse apiResponse = CustomersResponse.builder()
                .customers(Collections.singletonList(buildCustomerData())).build();

        Mockito.when(restTemplate.getForObject(eq("https://testapi.com"), eq(CustomersResponse.class)
                , Mockito.argThat(new ArgumentMatcher<Map<String, String>>() {
                    @Override
                    public boolean matches(Map<String, String> params) {
                        return true;

                    }
                }))).thenReturn(apiResponse);

        assertThrows(OrderException.class, () -> customerService.retrieveCustomers("amit@mail.com"));
    }

    private Customer buildCustomerData() {
        return Customer.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("User")
                .build();
    }
}
