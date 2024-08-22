package com.customer.CustomerService.service;

import com.cartservice.CartService.dtos.RequestCartDto;
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
import com.customer.CustomerService.feignclients.*;
import com.customer.CustomerService.models.Customer;
import com.customer.CustomerService.repository.CustomerRepository;
import com.identityservice.dtos.IdentityResponseDto;
import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.ktkapp.addressservice.models.Address;
import com.orders.OrdersService.dtos.RequestOrderDto;
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
import org.bouncycastle.asn1.esf.OtherRevRefs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private IdentityFeignClient identityFeignClient;

    private AddressFeignClient addressFeignClient;

    private ModelMapper modelMapper;

    private ProductFeignClient productFeignClient;

    private CartFeignClient cartFeignClient;

    private OrderFeignClient orderFeignClient;

    private PaymentFeignClient paymentFeignClient;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, IdentityFeignClient identityFeignClient, AddressFeignClient addressFeignClient, ModelMapper modelMapper, ProductFeignClient productFeignClient, CartFeignClient cartFeignClient, OrderFeignClient orderFeignClient, PaymentFeignClient paymentFeignClient) {
        this.customerRepository = customerRepository;
        this.identityFeignClient = identityFeignClient;
        this.addressFeignClient = addressFeignClient;
        this.modelMapper = modelMapper;
        this.productFeignClient = productFeignClient;
        this.cartFeignClient = cartFeignClient;
        this.orderFeignClient = orderFeignClient;
        this.paymentFeignClient = paymentFeignClient;
    }

    @Override
    public ResponseCustomerDto saveCustomer(RequestCustomerDto requestCustomerDto, String userName) {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        Customer customer = new Customer();
        if(requestCustomerDto.getCustomerName().length()<3) {
                throw new IllegalArgumentException("please enter valid Name.");
        }
        customer.setCustomerName(requestCustomerDto.getCustomerName());
        customer.setActive(true);

        customer.setEmailId(identityResponseDto.getEmailId());
        customer.setCreatedOn(new Date());
        customer.setUpdatedOn(new Date());
        if(!(requestCustomerDto.getMobile().length() == 10)) {
            throw new IllegalArgumentException("Please enter valid mobile number....");
        }
        customer.setMobile(requestCustomerDto.getMobile());

        Customer customer1 = customerRepository.save(customer);

        return modelMapper.map(customer1, ResponseCustomerDto.class);

    }

    @Override
    public ResponseCustomerDto getCustomer(String userName) throws CustomerDetailsNotAvailable {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        Customer customer = customerRepository.findByEmailId(identityResponseDto.getEmailId());
        if(customer.getCustomerName() == null ) {
            throw new CustomerDetailsNotAvailable("Please update customer details.....");
        }

        ResponseCustomerDto responseCustomerDto = new ResponseCustomerDto();
        responseCustomerDto.setCustomerName(customer.getCustomerName());
        responseCustomerDto.setMobile(customer.getMobile());
        responseCustomerDto.setEmailId(customer.getEmailId());
        responseCustomerDto.setActive(customer.isActive());
        responseCustomerDto.setUsername(identityResponseDto.getUsername());
        responseCustomerDto.setId(customer.getId());


        return responseCustomerDto;
    }

    @Override
    public List<ResponseCustomerDto> getAll(String userName) throws CustomerDetailsNotAvailable {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> auth = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();
        if(!auth.contains("ROLE_ADMIN")) {
            throw new CustomerDetailsNotAvailable("You are not authorized to get All Customers details........");
        }

        List<Customer> customers = customerRepository.findAll();

        return Arrays.asList(modelMapper.map(customers,ResponseCustomerDto[].class));

    }










    @Override
    public ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto, String username)  {

        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(username);

        EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
        emailAddressRequest.setEmailId(identityResponseDto.getEmailId());
        requestAddressDto.setEmailRequest(emailAddressRequest);

        return addressFeignClient.saveAddress(requestAddressDto);
    }

    @Override
    public List<ResponseAddressDto> getAddressesFromAddressService(String username) throws AddressNotFoundWithEmail {
        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(username);

        List<ResponseAddressDto> responseAddressDtos = addressFeignClient.getAddresses(identityResponseDto.getEmailId());

        if(responseAddressDtos == null) {
            throw new AddressNotFoundWithEmail("please add addresses in your Account.....");
        }

        return responseAddressDtos;
    }

    @Override
    public String deleteAddressWithZip(DeleteAddressRequest deleteAddressRequest, String userName) throws AddressNotFoundWithEmail {

        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(userName);

        EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
        emailAddressRequest.setEmailId(identityResponseDto.getEmailId());

        deleteAddressRequest.setEmailAddressRequest(emailAddressRequest);
        return addressFeignClient.deleteAddress(deleteAddressRequest);
    }


    @Override
    public ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest, String userName) throws AddressNotFoundWithEmail {

        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(userName);

        EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
        emailAddressRequest.setEmailId(identityResponseDto.getEmailId());

        updateAddressRequest.setEmailRequest(emailAddressRequest);

        return addressFeignClient.updateAddress(updateAddressRequest);
    }

    @Override
    public ResponseAddressDto getByZipAndEmail(RequestByZipAndEmailDto requestByZipAndEmailDto,String userName) throws AddressNotFoundWithEmail {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        return addressFeignClient.getByZipAddress(requestByZipAndEmailDto.getEmailId(),requestByZipAndEmailDto.getHouseNumber(),requestByZipAndEmailDto.getZip());
    }


    @Override
    public List<ResponseProductCustDto> getByShopName(String shopName) throws ProductsNotAvailableWithShopName {

        return productFeignClient.getProductsByShopName(shopName);
    }


    @Override
    public ResponseEntity<ResponseCartDto> saveCart(RequestCustCartDto requestCustCartDto, String userName) throws CartServiceUpdationException, ProductsNotAvailableWithProductName {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")){
            throw  new CartServiceUpdationException("you are not authorize to use Cart Service in www.localGrocery.com. Please sign up as Customer to use Cart...");
        }

        ResponseProductCustDto responseProductDto = productFeignClient.getProduct(requestCustCartDto.getProductName());

        RequestCartDto requestCartDto = new RequestCartDto();
        requestCartDto.setEmailId(identityResponseDto.getEmailId());
        requestCartDto.setProductName(responseProductDto.getProductName());
        requestCartDto.setPrice(responseProductDto.getPrice());
        requestCartDto.setShopName(responseProductDto.getShopName());
        requestCartDto.setQuantity(1);

        return cartFeignClient.saveCart(requestCartDto);

    }


    @Override
    public ResponseEntity<ResponseCartDto> updatecart(UpdateCartDto updateCartDto,String userName) throws CartServiceUpdationException, CartDetailsNotFound {
            IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);
            List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

            if(!authorization.contains("ROLE_CUSTOMER")){
                throw  new CartServiceUpdationException("you are not authorize to use Cart Service in www.localGrocery.com. Please sign up as Customer to use Cart...");
            }
            updateCartDto.setEmailId(identityResponseDto.getEmailId());

        return cartFeignClient.updatecart(updateCartDto);

    }


    @Override
    public List<ResponseCartDto> getAllProductsFromCart(String userName) throws CartDetailsNotFoundException, CartDetailsNotFound, CartServiceUpdationException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);
        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")){
            throw  new CartServiceUpdationException("you are not authorize to use Cart Service in www.localGrocery.com. Please sign up as Customer to use Cart...");
        }

        return cartFeignClient.getAllCart(identityResponseDto.getEmailId());
    }







    @Override
    public String order(String userName, RequestAddressCustDto requestAddressCustDto) throws AddressNotFoundWithEmail, CartServiceUpdationException, CartDetailsNotFound, OrdersNotPlacedException, PaymentFailedException {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")) {
            throw  new CartServiceUpdationException("you are not authorize to Order products in www.localGrocery.com. Please sign up as Customer to place orders...");
        }

        Customer responseCustomerDto = customerRepository.findByEmailId(identityResponseDto.getEmailId());

        List<ResponseCartDto> responseCartDtos = cartFeignClient.getAllCart(identityResponseDto.getEmailId());

        ResponseAddressDto address = addressFeignClient.getByHouseNumber(requestAddressCustDto.getHouseNumber());

        if(address == null) {

            throw  new AddressNotFoundWithEmail(requestAddressCustDto.getHouseNumber()+" Doesn't exists, You have entered wrong house Number please check....");
        }

        List<RequestOrderDto> requestOrderDtos = new ArrayList<>();

        for(ResponseCartDto responseCartDto : responseCartDtos) {

            RequestOrderDto requestOrderDto = new RequestOrderDto();

            requestOrderDto.setCustomerName(responseCustomerDto.getCustomerName());
            requestOrderDto.setPrice(responseCartDto.getPrice());
            requestOrderDto.setQuantity(responseCartDto.getQuantity());
            requestOrderDto.setCustomerEmailId(identityResponseDto.getEmailId());
            requestOrderDto.setProductName(responseCartDto.getProductName());
            requestOrderDto.setShopName(responseCartDto.getShopName());
            requestOrderDto.setZip(address.getZip());
            requestOrderDto.setHouseNumber(address.getHouseNumber());
            requestOrderDtos.add(requestOrderDto);

        }

        cartFeignClient.deleteCartProducts(identityResponseDto.getEmailId());

        return orderFeignClient.orderFromCart(requestOrderDtos);

    }


    @Override
    public List<ResponseOrderDto> getAllOrdersByCustomerEmail(String userName) throws  OrdersNotPlacedException, CustomerDetailsNotAvailable {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")){
            throw  new CustomerDetailsNotAvailable("you are not authorize to get All Products...");
        }

        return orderFeignClient.getAllOrdersByCustomerEmail(identityResponseDto.getEmailId());
    }

    @Override
    public List<ResponsePaymentDto> getAllPayments(String userName) throws PaymentsNotFound, CustomerDetailsNotAvailable {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")){
            throw  new CustomerDetailsNotAvailable("you are not authorize to get payment details...");
        }

        return paymentFeignClient.getAllPayments(identityResponseDto.getEmailId());
    }

    @Override
    public List<ResponseOrdersCustomerTotalDto> getCustomersTotalAmount(String userName) throws CustomerDetailsNotAvailable, OrdersNotPlacedException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")){
            throw  new CustomerDetailsNotAvailable("you are not authorize to get Orders total details...");
        }

        return orderFeignClient.getAllCustomersTotalAmountPerShop(identityResponseDto.getEmailId());
    }


    @Override
    public List<ResponseOrderCustomerDateDto> getCustomersTotalAndDate(String userName) throws CustomerDetailsNotAvailable, OrdersNotPlacedException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(!authorization.contains("ROLE_CUSTOMER")){
            throw  new CustomerDetailsNotAvailable("you are not authorize to get orders details...");
        }

        return orderFeignClient.getAllCustomerOrdersOnDate(identityResponseDto.getEmailId());
    }


    @Override
    public List<ResponseProductDto> getAllProducts() {
        return productFeignClient.getAllProducts();
    }

    @Override
    public List<ResponseProductDto> getProducts(int pageNo) {
        return productFeignClient.getProducts(pageNo);
    }

    @Override
    public void deleteCartProduct(String productName, String userName) {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        cartFeignClient.deleteCartProduct(productName,identityResponseDto.getEmailId());

    }
}
