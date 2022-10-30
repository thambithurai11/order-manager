package com.vodafoneziggo.ordermanager.controller;


import com.vodafoneziggo.ordermanager.model.OrderRequest;
import com.vodafoneziggo.ordermanager.db.entities.Orders;
import com.vodafoneziggo.ordermanager.service.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderServiceImpl orderService;

    private Orders orderEntity;

    @BeforeAll
    void init() {
        orderEntity = Orders
                .builder()
                .orderId(10L)
                .email("amit.vs@gmail.com")
                .firstName("Amit")
                .lastName("Vashishtha")
                .productId(1234L)
                .build();
    }

    @Test
    public void testGetAllOrderWithPagination() throws Exception {
        when(orderService.getAllOrders(0, 1)).thenReturn(Collections.singletonList(orderEntity));

        RequestBuilder productPage0Size1 = getAllRequestBuilder(0, 1);

        mockMvc.perform(productPage0Size1)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].orderId").value(10L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("amit.vs@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Amit"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").value("Vashishtha"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].productId").value(1234L));
    }

    @Test
    public void testCreateOrderForGivenEmailAndProductId() throws Exception {
        when(orderService.createOrder(Mockito.any())).thenReturn(orderEntity);

        RequestBuilder productPage0Size1 = postRequestBuilder(true);

        mockMvc.perform(productPage0Size1)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateOrder_WithInvalidRequest() throws Exception {

        RequestBuilder productPage0Size1 = postRequestBuilder(false);

        mockMvc.perform(productPage0Size1)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private RequestBuilder postRequestBuilder(boolean isValidRequest) throws JsonProcessingException {

        OrderRequest anObject = formOrderDTO(isValidRequest);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(anObject);
        return MockMvcRequestBuilders
                .post("/v1/orders")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    private OrderRequest formOrderDTO(boolean isValidRequest) {

        if (isValidRequest)
            return OrderRequest.builder()
                    .email("amit@mail.com")
                    .productId(10L).build();
        else return OrderRequest.builder()
                .build();
    }

    private RequestBuilder getAllRequestBuilder(int page, int size) {
        return MockMvcRequestBuilders
                .get("/v1/orders")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }
}
