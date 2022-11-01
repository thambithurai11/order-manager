package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.exception.OrderErrorCodes;
import com.vodafoneziggo.ordermanager.exception.OrderException;
import com.vodafoneziggo.ordermanager.model.Customer;
import com.vodafoneziggo.ordermanager.model.CustomersResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.vodafoneziggo.ordermanager.util.Constants.FIRST_PAGE;
import static com.vodafoneziggo.ordermanager.util.Constants.PAGE;
import static com.vodafoneziggo.ordermanager.util.Constants.PERPAGE;

/**
 * This is service class which has the implementation of retrieving existing customers
 *
 * @author Thambi Thurai Chinnadurai
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RestTemplate apiRestTemplate;

    @Value("${order.api.endpoint}")
    private String endpoint;

    @Value("${order.api.perPage}")
    private Integer perPage;

    /**
     * This method is used to retrieve the existing customer for the given email address
     *
     * @param email
     * @return Existing Customer
     */
    @Override
    public Customer retrieveCustomers(String email) {

        CustomersResponse customers = retrieveUsers(FIRST_PAGE);

        Optional<Customer> userFound = filterUser(email, customers.getCustomers());

        return userFound.orElseGet(() -> retrieveCustomerFromNextPages(email, customers.getTotalPages()));
    }


    private CustomersResponse retrieveUsers(Integer page) {

        Map<String, Object> params = new HashMap<>();
        params.put(PAGE, page);
        params.put(PERPAGE, perPage);

        return apiRestTemplate.getForObject(endpoint, CustomersResponse.class, params);
    }


    private Customer retrieveCustomerFromNextPages(String email, int totalPages) {

        int nextPage = FIRST_PAGE;
        while (nextPage < totalPages) {
            nextPage++;
            CustomersResponse customers = retrieveUsers(nextPage);
            Optional<Customer> userFound = filterUser(email, customers.getCustomers());
            if (userFound.isPresent()) return userFound.get();
        }
        log.error("User not found for email : {}", email);
        throw new OrderException(OrderErrorCodes.NOT_FOUND, "User not found for email : " + email);
    }

    private Optional<Customer> filterUser(String email, List<Customer> customers) {
        return customers.stream().filter(user -> StringUtils.equals(email, user.getEmail())).findFirst();
    }
}
