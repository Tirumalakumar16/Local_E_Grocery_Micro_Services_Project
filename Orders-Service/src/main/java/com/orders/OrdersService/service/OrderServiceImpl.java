package com.orders.OrdersService.service;

import com.orders.OrdersService.dtos.RequestOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.feignclients.PaymentFeignClient;
import com.orders.OrdersService.models.OrderDetails;
import com.orders.OrdersService.repository.OrderRepository;
import com.payment.PaymentService.dtos.RequestPaymentDto;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.models.PaymentMode;
import com.payment.PaymentService.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    private ModelMapper modelMapper;

    private PaymentFeignClient paymentFeignClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, PaymentFeignClient paymentFeignClient) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.paymentFeignClient = paymentFeignClient;
    }

    @Override
    public String orderFromCart(List<RequestOrderDto> requestOrderDto) {


        List<OrderDetails> orderDetails = new ArrayList<>();
        for(RequestOrderDto requestOrderDto1: requestOrderDto) {

            OrderDetails orderDetails1 = new OrderDetails();

            orderDetails1.setPrice(requestOrderDto1.getPrice());
            orderDetails1.setCreatedAt(new Date());
            orderDetails1.setQuantity(requestOrderDto1.getQuantity());
            orderDetails1.setZip(requestOrderDto1.getZip());
            orderDetails1.setCustomerName(requestOrderDto1.getCustomerName());
            orderDetails1.setHouseNumber(requestOrderDto1.getHouseNumber());
            orderDetails1.setShopName(requestOrderDto1.getShopName());
            orderDetails1.setCustomerEmailId(requestOrderDto1.getCustomerEmailId());
            orderDetails1.setProductName(requestOrderDto1.getProductName());
            
            orderDetails.add(orderDetails1);

        }

        double totalAmount =0;

        for(OrderDetails orderDetails1:orderDetails) {
            
            
            totalAmount = totalAmount+(orderDetails1.getPrice()*orderDetails1.getQuantity());
            
            
        }

        RequestPaymentDto requestPaymentDto= mapToPayment(requestOrderDto.get(0));
        requestPaymentDto.setTotalAmount(totalAmount);

        ResponsePaymentDto responsePaymentDto = paymentFeignClient.pay(requestPaymentDto);

        List<OrderDetails> updatedOrders = new ArrayList<>();
        for(OrderDetails orderDetails1 : orderDetails) {
            orderDetails1.setTransactionId(responsePaymentDto.getTransactionId());

            updatedOrders.add(orderDetails1);

        }


        List<OrderDetails> orderDetails1 = orderRepository.saveAll(updatedOrders);

        return "Order Placed successfully ......";
        

    }

    private RequestPaymentDto mapToPayment(RequestOrderDto requestOrderDto) {

        RequestPaymentDto requestPaymentDto = new RequestPaymentDto();
        requestPaymentDto.setPaymentMode(PaymentMode.ONLINE);
        requestPaymentDto.setCustomerName(requestOrderDto.getCustomerName());
        requestPaymentDto.setCustomerEmailId(requestOrderDto.getCustomerEmailId());
        return requestPaymentDto;

    }


    @Override
    public List<ResponseOrderDto> getAllOrdersByShopName(String shopName) throws OrdersNotPlacedException {

        List<OrderDetails> orderDetails = orderRepository.findByShopName(shopName);
        if(orderDetails.isEmpty()) {
            throw new OrdersNotPlacedException("Sorry ...From your Shop not ordered yet.....");
        }
        return Arrays.asList(modelMapper.map(orderDetails, ResponseOrderDto[].class));
    }


    @Override
    public List<ResponseOrderDto> getAllOrdersByCustomerEmail(String customerEmail) throws OrdersNotPlacedException {

        List<OrderDetails> orderDetails = orderRepository.findByCustomerEmailId(customerEmail);

        if(orderDetails.isEmpty()) {
            throw new OrdersNotPlacedException("Sorry ...Please Order to get All orders.....");
        }
        return Arrays.asList(modelMapper.map(orderDetails, ResponseOrderDto[].class));
    }
}
