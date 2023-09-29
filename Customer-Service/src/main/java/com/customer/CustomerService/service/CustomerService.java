package com.customer.CustomerService.service;

import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.customer.CustomerService.dtos.RequestAddressCustDto;
import com.customer.CustomerService.dtos.RequestCustomerDto;
import com.customer.CustomerService.dtos.ResponseCustomerDto;
import com.customer.CustomerService.dtos.cart.RequestCustCartDto;
import com.customer.CustomerService.dtos.order.ResponseCustOrderDto;
import com.customer.CustomerService.exceptions.CartDetailsNotFoundException;
import com.customer.CustomerService.exceptions.CartServiceUpdationException;
import com.customer.CustomerService.exceptions.CustomerDetailsNotAvailable;
import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;
import com.products.ProductService.dtos.ResponseProductCustDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    ResponseCustomerDto saveCustomer(RequestCustomerDto requestCustomerDto, String userName);

    ResponseCustomerDto getCustomer(String userName) throws CustomerDetailsNotAvailable;

    List<ResponseCustomerDto> getAll(String userName) throws CustomerDetailsNotAvailable;






    ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto, String userName);

    List<ResponseAddressDto> getAddressesFromAddressService(String username) throws AddressNotFoundWithEmail;

    String deleteAddressWithZip(DeleteAddressRequest deleteAddressRequest, String userName);

    ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest, String userName);



    ResponseAddressDto getByZipAndEmail(RequestByZipAndEmailDto requestByZipAndEmailDto, String userName);


    List<ResponseProductCustDto> getByShopName(String shopName);


    ResponseEntity<ResponseCartDto> saveCart(RequestCustCartDto requestCustCartDto, String userName) throws CartServiceUpdationException;

    ResponseEntity<ResponseCartDto> updatecart(UpdateCartDto updateCartDto,String userName) throws CartServiceUpdationException;

    List<ResponseCartDto> getAllProductsFromCart(String userName) throws CartDetailsNotFoundException;

    String order(String userName, RequestAddressCustDto addressCustDto) throws AddressNotFoundWithEmail, CartServiceUpdationException;

    List<ResponseOrderDto> getAllOrdersByCustomerEmail(String userName) throws CartServiceUpdationException, OrdersNotPlacedException;

    List<ResponsePaymentDto> getAllPayments(String userName) throws CartServiceUpdationException, PaymentsNotFound;
}
