package com.orders.OrdersService.service;

import com.orders.OrdersService.dtos.RequestOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto;
import com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.exceptions.PaymentFailedException;
import com.orders.OrdersService.feignclients.PaymentFeignClient;
import com.orders.OrdersService.feignclients.ProductFeignClient;
import com.orders.OrdersService.models.OrderDetails;
import com.orders.OrdersService.repository.OrderRepository;
import com.payment.PaymentService.dtos.RequestPaymentDto;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.models.PaymentMode;
import com.products.ProductService.dtos.RequestCustomerProductDto;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    private ModelMapper modelMapper;

    private PaymentFeignClient paymentFeignClient;

    private ProductFeignClient productFeignClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, PaymentFeignClient paymentFeignClient, ProductFeignClient productFeignClient) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.paymentFeignClient = paymentFeignClient;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public String orderFromCart(List<RequestOrderDto> requestOrderDto) throws PaymentFailedException, OrdersNotPlacedException, ProductsNotAvailableWithProductName {

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

        List<RequestCustomerProductDto> requestList = new ArrayList<>();

        for (OrderDetails orderDetails1 : orderDetails) {
            RequestCustomerProductDto requestCustomerProductDto = new RequestCustomerProductDto();
            requestCustomerProductDto.setProductName(orderDetails1.getProductName());
            requestCustomerProductDto.setQuantity(orderDetails1.getQuantity());
            requestCustomerProductDto.setShopName(orderDetails1.getShopName());
            requestList.add(requestCustomerProductDto);
        }

        List<RequestCustomerProductDto> updatedListForCustomerUpdation = new ArrayList<>();

        for(RequestCustomerProductDto requestCustomerProductDto: requestList) {

            ResponseProductCustDto responseProductDto = productFeignClient.getProduct(requestCustomerProductDto.getProductName());
            int quantity = responseProductDto.getQuantity();
            if(quantity<requestCustomerProductDto.getQuantity()) {
                throw new OrdersNotPlacedException("Orders not placed Stock not available.");
            }
            int realQnty = quantity-requestCustomerProductDto.getQuantity();
            requestCustomerProductDto.setQuantity(realQnty);

            updatedListForCustomerUpdation.add(requestCustomerProductDto);
        }

        double totalAmount =0;

        for(OrderDetails orderDetails1:orderDetails) {

            totalAmount = totalAmount+(orderDetails1.getPrice()*orderDetails1.getQuantity());

        }

        RequestPaymentDto requestPaymentDto= mapToPayment(requestOrderDto.get(0));
        requestPaymentDto.setTotalAmount(totalAmount);

        ResponsePaymentDto responsePaymentDto = paymentFeignClient.pay(requestPaymentDto);

        if(requestPaymentDto.getCustomerName().isEmpty()) {
            throw new PaymentFailedException("Orders not placed please try again");
        }

        List<OrderDetails> updatedOrders = new ArrayList<>();
        for(OrderDetails orderDetails1 : orderDetails) {
            orderDetails1.setTransactionId(responsePaymentDto.getTransactionId());

            updatedOrders.add(orderDetails1);

        }

        List<OrderDetails> orderDetails1 = orderRepository.saveAll(updatedOrders);

        for (RequestCustomerProductDto requestCustomerProductDto : updatedListForCustomerUpdation) {

                 productFeignClient.updateByCustomer(requestCustomerProductDto);
        }

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


    @Override
    public List<ResponseOrderShopDto> getAllCustomersPerShopOrders(String shopName) throws OrdersNotPlacedException {

        List<ResponseOrderShopDto> responseOrderShopDtos =orderRepository.findTotalByCustomerWise(shopName);

        if (responseOrderShopDtos.isEmpty()) {
            throw new OrdersNotPlacedException("Orders Not found on your shop Name..");
        }

        return responseOrderShopDtos;
    }


    @Override
    public List<ResponseOrdersShopTotalDto> getAllCustomersTotalAmountPerShopOrders(String shopName) throws OrdersNotPlacedException {

        List<ResponseOrdersShopTotalDto> responseOrdersShopTotalDtos = orderRepository.findTotalAmountOfCustomersPerShop(shopName);

        if (responseOrdersShopTotalDtos.isEmpty()) {
            throw new OrdersNotPlacedException("Orders Not found on your shop Name..");
        }

        return responseOrdersShopTotalDtos;
    }

    @Override
    public List<ResponseOrdersCustomerTotalDto> getAllCustomersTotalAmountPerShop(String email) throws OrdersNotPlacedException {
        List<ResponseOrdersCustomerTotalDto> responseOrdersShopTotalDtos = orderRepository.findTotalAmountForCustomerPerShop(email);

        if (responseOrdersShopTotalDtos.isEmpty()) {
            throw new OrdersNotPlacedException("Orders Not found on "+email+", Please Order to get total purchases.");
        }

        return responseOrdersShopTotalDtos;
    }


    @Override
    public List<ResponseOrderCustomerDateDto> getAllCustomerOrdersOnDate(String email) throws OrdersNotPlacedException {

        List<ResponseOrderCustomerDateDto> responseOrdersShopTotalDtos = orderRepository.findAllOrdersOnDateForCustomer(email);

        if (responseOrdersShopTotalDtos.isEmpty()) {
            throw new OrdersNotPlacedException("Orders Not found on "+email+", Please order to see all Orders on Date.");
        }

        return responseOrdersShopTotalDtos;
    }
}
