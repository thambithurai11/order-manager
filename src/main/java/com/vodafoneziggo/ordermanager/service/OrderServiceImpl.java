package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.db.entity.Orders;
import com.vodafoneziggo.ordermanager.db.repo.OrderRepository;
import com.vodafoneziggo.ordermanager.exception.OrderErrorCodes;
import com.vodafoneziggo.ordermanager.exception.OrderException;
import com.vodafoneziggo.ordermanager.model.Customer;
import com.vodafoneziggo.ordermanager.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * This is Service class which has the implementation of creating the order and
 * retrieving the existing orders from database
 *
 * @author Thambi Thurai Chinnadurai
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Executor taskExecutor;

    /**
     * This method is used to create new Order for given orderRequest
     *
     * @param orderRequest orderDto
     * @return OrderId
     */
    @Override
    public Orders createOrder(OrderRequest orderRequest) {

        log.info("Create Order for emailId: {} ProductId: {}", orderRequest.getEmail(), orderRequest.getProductId());
        CompletableFuture<Customer> customerDetails = CompletableFuture.supplyAsync(() ->
                                                                                            customerService.retrieveCustomers(
                                                                                                    orderRequest.getEmail()),
                                                                                    taskExecutor);

        CompletableFuture<Long> productId = CompletableFuture.supplyAsync(() ->
                                                                                  isOrderAlreadyExists(
                                                                                          orderRequest.getProductId(),
                                                                                          orderRequest.getEmail()),
                                                                          taskExecutor);
        CompletableFuture.allOf(customerDetails, productId);
        try {
            Customer customer = customerDetails.get();
            Orders orderEntity = Orders.builder()
                    .email(customer.getEmail())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .productId(productId.get()).build();
            return orderRepository.save(orderEntity);

        } catch (InterruptedException | ExecutionException exception) {
            log.error("Error occurred while creating order");
            if (exception.getCause() instanceof OrderException)
                throw (OrderException) exception.getCause();
            else
                log.error(exception.getLocalizedMessage());
        }
        return null;
    }

    /**
     * This method is used to retrieve all the {@link Orders} from database with pagination support
     *
     * @param page page number
     * @param size number of results in one page
     * @return list of {@link Orders}
     */
    @Override
    public List<Orders> getAllOrders(int page, int size) {

        List<Orders> existingOrders = orderRepository.findAll(PageRequest.of(page, size)).getContent();
        if (CollectionUtils.isEmpty(existingOrders)) {
            log.error("No record found");
            throw new OrderException(OrderErrorCodes.NOT_FOUND, "No Record found");
        }
        return existingOrders;
    }

    private long isOrderAlreadyExists(long productId, String email) {

        Optional<Orders> orderFound = orderRepository.findByProductIdAndEmail(productId, email);
        if (orderFound.isPresent()) {
            log.error("Order already exists for productId : {}", productId);
            throw new OrderException(OrderErrorCodes.INVALID_ORDER,
                                     String.format("Order already exists for productId : %s", productId));
        }
        return productId;
    }
}
