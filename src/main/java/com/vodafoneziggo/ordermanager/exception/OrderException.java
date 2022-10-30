package com.vodafoneziggo.ordermanager.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *  OrderException
 *  RuntimeException to handle expected exceptions and Map them to {@link OrderError}
 */
@Getter
@Setter
public class OrderException extends RuntimeException  {

    private final OrderErrorCodes errorCode;
    private final String message;

    public OrderException(OrderErrorCodes errorCode, String message){
        super(message);
        this.errorCode= errorCode;
        this.message=message;
    }

}
