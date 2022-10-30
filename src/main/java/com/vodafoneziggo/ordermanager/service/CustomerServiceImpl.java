package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.model.Customer;
import com.vodafoneziggo.ordermanager.model.CustomersResponse;
import com.vodafoneziggo.ordermanager.exception.OrderErrorCodes;
import com.vodafoneziggo.ordermanager.exception.OrderException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.vodafoneziggo.ordermanager.util.Constants.FIRST_PAGE;
import static com.vodafoneziggo.ordermanager.util.Constants.PAGE;
import static com.vodafoneziggo.ordermanager.util.Constants.PERPAGE;

/**
 * CustomerApiClient class
 * class to call Api
 */
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RestTemplate apiRestTemplate;

    @Value("${order.api.endpoint}")
    private String endpoint;

    @Value("${order.api.perPage}")
    private Integer perPage;

    @Override
    public Customer retrieveCustomers(String email) {


        CustomersResponse customers = getUsersResponse(FIRST_PAGE);

        Optional<Customer> userFound = filterUserFromResponse(email, customers.getCustomers());

        return userFound.orElseGet(() -> retrieveUsersFromNextPages(email, customers.getTotalPages()));
    }


    private CustomersResponse getUsersResponse(Integer page) {

        Map<String, Object> params = new HashMap<>();
        params.put(PAGE, page);
        params.put(PERPAGE, perPage);

        return apiRestTemplate.getForObject(endpoint, CustomersResponse.class, params);
    }

    private Optional<Customer> filterUserFromResponse(String email, List<Customer> customers) {

        return customers.stream().filter(data -> StringUtils.equals(email, data.getEmail())).findFirst();
    }

    private Customer retrieveUsersFromNextPages(String email, int totalPages) {

        int currentPage = FIRST_PAGE;
        while (currentPage < totalPages) {
            currentPage++;
            CustomersResponse customers = getUsersResponse(currentPage);
            Optional<Customer> userFound = filterUserFromResponse(email, customers.getCustomers());
            if (userFound.isPresent()) return userFound.get();
        }
        throw new OrderException(OrderErrorCodes.NOT_FOUND, "User not found for email : " + email);
    }
}
