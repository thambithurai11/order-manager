package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.db.entity.Orders;
import com.vodafoneziggo.ordermanager.exception.OrderErrorCodes;
import com.vodafoneziggo.ordermanager.db.repo.OrderRepository;
import com.vodafoneziggo.ordermanager.model.Customer;
import com.vodafoneziggo.ordermanager.model.OrderRequest;
import com.vodafoneziggo.ordermanager.exception.OrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * OrderExecutor class
 * Service class to execute order request
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final Executor taskExecutor;

    /**
     * create Order for given productId
     *
     * @param order orderDto
     * @return OrderId
     */
    @Override
    public Orders createOrder(OrderRequest order) {

        log.info("Create Order for emailId: {} ProductId: {}", order.getEmail(), order.getProductId());
        CompletableFuture<Customer> customerDetails = CompletableFuture.supplyAsync(() ->
                                                                                            customerService.retrieveCustomers(
                                                                                                    order.getEmail()),
                                                                                    taskExecutor);

        CompletableFuture<Long> productId = CompletableFuture.supplyAsync(() ->
                                                                                  isProductExists(
                                                                                          order.getProductId(),
                                                                                          order.getEmail()),
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
            log.error("exception occurred while creating order");
            if (exception.getCause() instanceof OrderException)
                throw (OrderException) exception.getCause();
            else
                log.error(exception.getLocalizedMessage());
        }
        return null;
    }

    /**
     * gets all the {@link Orders} from database with pagination support
     *
     * @param page page number
     * @param size number of results in one page
     * @return list of {@link Orders}
     */
    @Override
    public List<Orders> getAllOrders(int page, int size) {

        List<Orders> orderEntities = orderRepository.findAll(PageRequest.of(page, size)).getContent();
        if (CollectionUtils.isEmpty(orderEntities)) {
            log.error("No record found");
            throw new OrderException(OrderErrorCodes.NOT_FOUND, "No Record found");
        }
        return orderEntities;
    }

    private long isProductExists(long productId, String email) {

        log.info("productId to check into db : {}", productId);
        Optional<Orders> orderEntity = orderRepository.findByProductIdAndEmail(productId, email);
        if (orderEntity.isPresent()) {
            log.error("Order already created for productId : {}", productId);
            throw new OrderException(OrderErrorCodes.INVALID_ORDER,
                                     String.format("Order already exists for productId : %s", productId));
        }
        return productId;
    }
}
