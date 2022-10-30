package com.vodafoneziggo.ordermanager.service;

import com.vodafoneziggo.ordermanager.model.Customer;
import com.vodafoneziggo.ordermanager.model.OrderRequest;
import com.vodafoneziggo.ordermanager.db.entities.Orders;
import com.vodafoneziggo.ordermanager.exception.OrderErrorCodes;
import com.vodafoneziggo.ordermanager.exception.OrderException;
import com.vodafoneziggo.ordermanager.db.repos.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {


    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private CustomerServiceImpl customerApiClient;

    @Mock
    private Executor taskExecutor;

    private Orders forOrderEntity() {
        return Orders
                .builder()
                .orderId(10L)
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("Vashishtha")
                .productId(1234L)
                .build();
    }

    @Test
    public void test_GetAll_Orders() {
        List<Orders> orderEntityList = new ArrayList<>();
        orderEntityList.add(forOrderEntity());
        Page<Orders> pagedTasks = new PageImpl<>(orderEntityList);
        when(orderRepository.findAll(PageRequest.of(0, 1))).thenReturn(pagedTasks);
        List<Orders> orderEntities = orderService.getAllOrders(0, 1);
        assertEquals(10L, orderEntities.get(0).getOrderId());
        assertEquals("test@gmail.com", orderEntities.get(0).getEmail());
        assertEquals("Test", orderEntities.get(0).getFirstName());
        assertEquals("Vashishtha", orderEntities.get(0).getLastName());
        assertEquals(1234L, orderEntities.get(0).getProductId());

    }

    @Test
    public void test_No_Order_Available_Exception() {
        List<Orders> orderEntityList = new ArrayList<>();
        Page<Orders> pagedTasks = new PageImpl<>(orderEntityList);
        when(orderRepository.findAll(PageRequest.of(0, 1))).thenReturn(pagedTasks);
        assertThrows(OrderException.class, () -> orderService.getAllOrders(0, 1));

    }


    @Test
    public void testCreateOrderWithProductIdAndEmailId() {
        doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(taskExecutor).execute(any(Runnable.class));
        when(customerApiClient.retrieveCustomers(anyString())).thenReturn(forData());
        when(orderRepository.findByProductIdAndEmail(Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.empty());
        when(orderRepository.save(any())).thenReturn(forOrderEntity());
        Orders response = orderService.createOrder(formOrderDto());
        assertNotNull(response.getOrderId());
    }

    @Test
    public void test_CreateOrderWithInvalidEmailId() {
        doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(taskExecutor).execute(any(Runnable.class));
        when(customerApiClient.retrieveCustomers(anyString())).thenThrow(new OrderException(OrderErrorCodes.NOT_FOUND, "emailId not found"));
        assertThrows(OrderException.class, () -> orderService.createOrder(formOrderDto()));
    }

    @Test
    public void test_CreateOrderWithInvalidProductId() {
        doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(taskExecutor).execute(any(Runnable.class));
        when(customerApiClient.retrieveCustomers(anyString())).thenReturn(forData());
        when(orderRepository.findByProductIdAndEmail(Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.of(forOrderEntity()));
        assertThrows(OrderException.class, () -> orderService.createOrder(formOrderDto()));
    }

    private Customer forData() {
        return Customer.builder()
                .email("test@gmail.com")
                .firstName("Test")
                .lastName("User")
                .build();
    }

    private OrderRequest formOrderDto() {
        return OrderRequest.builder()
                .email("test@gmail.com")
                .productId(1234L).build();
    }
}
