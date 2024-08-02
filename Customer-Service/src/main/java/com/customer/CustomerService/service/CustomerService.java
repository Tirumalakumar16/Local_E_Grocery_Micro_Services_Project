package com.customer.CustomerService.service;

import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
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
import com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto;
import com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.exceptions.PaymentFailedException;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    ResponseCustomerDto saveCustomer(RequestCustomerDto requestCustomerDto, String userName);

    ResponseCustomerDto getCustomer(String userName) throws CustomerDetailsNotAvailable;

    List<ResponseCustomerDto> getAll(String userName) throws CustomerDetailsNotAvailable;






    ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto, String userName) ;

    List<ResponseAddressDto> getAddressesFromAddressService(String username) throws AddressNotFoundWithEmail;

    String deleteAddressWithZip(DeleteAddressRequest deleteAddressRequest, String userName) throws AddressNotFoundWithEmail;

    ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest, String userName) throws AddressNotFoundWithEmail;



    ResponseAddressDto getByZipAndEmail(RequestByZipAndEmailDto requestByZipAndEmailDto, String userName) throws AddressNotFoundWithEmail;


    List<ResponseProductCustDto> getByShopName(String shopName) throws ProductsNotAvailableWithShopName;


    ResponseEntity<ResponseCartDto> saveCart(RequestCustCartDto requestCustCartDto, String userName) throws CartServiceUpdationException, ProductsNotAvailableWithProductName;

    ResponseEntity<ResponseCartDto> updatecart(UpdateCartDto updateCartDto,String userName) throws CartServiceUpdationException, CartDetailsNotFound;

    List<ResponseCartDto> getAllProductsFromCart(String userName) throws CartDetailsNotFoundException, CartDetailsNotFound, CartServiceUpdationException;

    String order(String userName, RequestAddressCustDto addressCustDto) throws AddressNotFoundWithEmail, CartServiceUpdationException, CartDetailsNotFound, OrdersNotPlacedException, PaymentFailedException;

    List<ResponseOrderDto> getAllOrdersByCustomerEmail(String userName) throws  OrdersNotPlacedException, CustomerDetailsNotAvailable;

    List<ResponsePaymentDto> getAllPayments(String userName) throws  PaymentsNotFound, CustomerDetailsNotAvailable;

    List<ResponseOrdersCustomerTotalDto> getCustomersTotalAmount(String userName) throws CustomerDetailsNotAvailable, OrdersNotPlacedException;

    List<ResponseOrderCustomerDateDto> getCustomersTotalAndDate(String userName) throws CustomerDetailsNotAvailable, OrdersNotPlacedException;

    List<ResponseProductDto> getAllProducts();

    List<ResponseProductDto> getProducts(int pageNo);

    void deleteCartProduct(String productName, String userName);
}
