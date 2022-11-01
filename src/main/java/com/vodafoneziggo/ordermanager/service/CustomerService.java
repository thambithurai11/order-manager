package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.model.Customer;

public interface CustomerService {

    Customer retrieveCustomers(String email);
}
