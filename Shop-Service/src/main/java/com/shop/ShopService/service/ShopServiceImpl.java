package com.shop.ShopService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identityservice.dtos.IdentityResponseDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.shop.ShopService.config.KafkaPublisherClient;
import com.shop.ShopService.dtos.KafkaMessage;
import com.shop.ShopService.dtos.RequestShopDtos;
import com.shop.ShopService.dtos.ResponseShopCustDto;
import com.shop.ShopService.dtos.ResponseShopDto;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.shop.ShopService.dtos.product.RequestProductShopDto;
import com.shop.ShopService.exceptions.ShopIsNotFoundException;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.exceptions.UserNotFound;
import com.shop.ShopService.feignclients.AddressFeignClient;
import com.shop.ShopService.feignclients.IdentityFeignClient;
import com.shop.ShopService.feignclients.OrderFeignClient;
import com.shop.ShopService.feignclients.ProductFeignClient;
import com.shop.ShopService.models.Shop;
import com.shop.ShopService.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{

    private ShopRepository shopRepository;

    private ModelMapper modelMapper;

    private IdentityFeignClient identityFeignClient;

    private AddressFeignClient addressFeignClient;

    private ProductFeignClient productFeignClient;
    private OrderFeignClient orderFeignClient;
    private ObjectMapper objectMapper;
    private KafkaPublisherClient kafkaPublisherClient;
    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper, IdentityFeignClient identityFeignClient, AddressFeignClient addressFeignClient, ProductFeignClient productFeignClient, OrderFeignClient orderFeignClient, ObjectMapper objectMapper, KafkaPublisherClient kafkaPublisherClient) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.identityFeignClient = identityFeignClient;
        this.addressFeignClient = addressFeignClient;
        this.productFeignClient = productFeignClient;
        this.orderFeignClient = orderFeignClient;
        this.objectMapper = objectMapper;
        this.kafkaPublisherClient = kafkaPublisherClient;
    }

    @Override
    public ResponseShopDto saveShop(RequestShopDtos requestShopDtos,String userName) throws UserNotFound {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> owner = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(owner.contains("ROLE_CUSTOMER")){
            throw new UserNotFound("You dont have access to upload a shop, please signup as owner...");
        }
        ResponseAddressDto responseAddressDto = addressFeignClient.getAddresses(identityResponseDto.getEmailId()).get(0);

        Shop shop = mapToShop(requestShopDtos,responseAddressDto,identityResponseDto);

        Shop shop1 = shopRepository.save(shop);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setTo(identityResponseDto.getEmailId());
        kafkaMessage.setSubject("Shop Registration");
        kafkaMessage.setBody("Your shop is registered successfully with www.localGrocery.com\n\n" +
                "Shop Name: "+shop1.getShopName()+"\n" +
                "Owner Name: "+shop1.getOwnerName()+"\n" +
                "City: "+shop1.getCity()+"\n" +
                "Mobile: "+shop1.getMobile()+"\n" +
                "Email: "+shop1.getEmailId()+"\n\n" +
                "Thank you for registering with us..");
        try {
            kafkaPublisherClient.sendMessage("shopRegistration",objectMapper.writeValueAsString(kafkaMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return modelMapper.map(shop1, ResponseShopDto.class);

    }
    private Shop mapToShop(RequestShopDtos requestShopDtos,ResponseAddressDto responseAddressDto,IdentityResponseDto identityResponseDto) {
        Shop shop = new Shop();

        shop.setShopName(requestShopDtos.getShopName());
        shop.setCity(responseAddressDto.getCity());
        shop.setActive(true);
        shop.setMobile(requestShopDtos.getMobile());
        shop.setOwnerName(requestShopDtos.getOwnerName());
        shop.setProductsRegistered(0);
        shop.setRegisteredOn(new Date());
        shop.setUpdatedOn(new Date());
        shop.setEmailId(identityResponseDto.getEmailId());
        return shop;
    }


    @Override
    public List<ResponseShopDto> getAll(String userName) throws UserNotAutherizedException, ShopIsNotFoundException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);
        List<String> owner = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(owner.contains("ROLE_CUSTOMER") && owner.size() == 1) {
            throw new UserNotAutherizedException("You dont have access to get a shops, please signup as owner...  "+identityResponseDto.getEmailId());
        }
        List<Shop> shops = shopRepository.findAll();
        if(shops.isEmpty()) {
            throw new ShopIsNotFoundException("No shops are registered yet..");
        }

        return Arrays.asList(modelMapper.map(shops, ResponseShopDto[].class));
    }

    @Override
    public List<ResponseShopCustDto> findByCity(String city) throws ShopIsNotFoundException {

        List<Shop> shops = shopRepository.findByCity(city);

        if(shops.isEmpty()) {
            throw new ShopIsNotFoundException("No shops are registered yet on this City "+city);
        }

        return Arrays.asList(modelMapper.map(shops, ResponseShopCustDto[].class));
    }


    @Override
    public ResponseProductDto saveProduct(RequestProductShopDto requestProductDto, String userName) throws UserNotFound, ShopIsNotFoundException {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);
        Shop shop = shopRepository.findByEmailId(identityResponseDto.getEmailId());

        if(shop == null) {

            throw new ShopIsNotFoundException("Please add your shop to www.localGrocery.com.");
        }

        List<String> auth = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();
        if(auth.contains("ROLE_CUSTOMER") && auth.size()==1){
            throw new UserNotFound("Please register your shop with www.localGrocery.com... signup as Owner.. ");
        }
        RequestProductDto requestProductDto1 = mapToProductDto(requestProductDto,shop,identityResponseDto);

        shop.setProductsRegistered(shop.getProductsRegistered()+1);
        Shop shop1 = shopRepository.save(shop);

        return productFeignClient.saveProduct(requestProductDto1);

    }
    private RequestProductDto mapToProductDto(RequestProductShopDto requestProductDto,Shop shop,IdentityResponseDto identityResponseDto){
        RequestProductDto requestProductDto1 = new RequestProductDto();
        requestProductDto1.setProductName(requestProductDto.getProductName());
        requestProductDto1.setPrice(requestProductDto.getPrice());
        requestProductDto1.setQuantity(requestProductDto.getQuantity());
        requestProductDto1.setEmailId(identityResponseDto.getEmailId());
        requestProductDto1.setCategory(requestProductDto.getCategory());
        requestProductDto1.setShopName(shop.getShopName());
        return requestProductDto1;
    }


    @Override
    public List<ResponseProductDto> getAllProducts(String userName) throws UserNotFound, ProductsNotAvailableWithProductAndSellerEmail {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> auth = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();
        if(auth.contains("ROLE_CUSTOMER") && auth.size()==1){
            throw new UserNotFound("Please register your shop with www.localGrocery.com... signup as Owner.."+identityResponseDto.getEmailId());
        }

        return productFeignClient.getByEmail(identityResponseDto.getEmailId());
    }


    @Override
    public ResponseProductDto updateProduct(String userName, RequestOwnerDto requestOwnerDto) throws UserNotAutherizedException, ProductsNotAvailableWithProductAndSellerEmail, ShopIsNotFoundException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);
        List<String> owner = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(owner.contains("ROLE_CUSTOMER") && owner.size() == 1) {
            throw new UserNotAutherizedException("You are UnAuthorized to Update product in a shop...  "+identityResponseDto.getEmailId());
        }

        Shop shop = shopRepository.findByEmailId(identityResponseDto.getEmailId());

        if(shop == null) {

            throw new ShopIsNotFoundException("Please add your shop to www.localGrocery.com.");
        }
        requestOwnerDto.setEmailId(identityResponseDto.getEmailId());
        requestOwnerDto.setShopName(shop.getShopName());

        return productFeignClient.updateProduct(requestOwnerDto);
    }


    @Override
    public List<ResponseOrderDto> getAllOrdersByOwnerEmailId(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> owner = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(owner.contains("ROLE_CUSTOMER") && owner.size() == 1) {
            throw new UserNotAutherizedException("You are UnAuthorized to get Orders from a shop...  "+identityResponseDto.getEmailId());
        }

        Shop shop = shopRepository.findByEmailId(identityResponseDto.getEmailId());

        if(shop == null) {

            throw new ShopIsNotFoundException("Please add your shop to www.localGrocery.com.");
        }

        return orderFeignClient.getAllOrdersByShopName(shop.getShopName());
    }


    @Override
    public List<ResponseOrderShopDto> getAllCustomersPerShopOrders(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);
        List<String> owner = Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if(owner.contains("ROLE_CUSTOMER") && owner.size() == 1) {
            throw new UserNotAutherizedException("You are UnAuthorized to get Orders from a shop...  "+identityResponseDto.getEmailId());
        }
        Shop shop = shopRepository.findByEmailId(identityResponseDto.getEmailId());
        if(shop == null) {

            throw new ShopIsNotFoundException("Please add your shop to www.localGrocery.com.");
        }

        return orderFeignClient.getAllCustomersPerShopOrders(shop.getShopName());
    }

    @Override
    public List<ResponseOrdersShopTotalDto> getTotalAmountForEveryCustomer(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException {
        IdentityResponseDto identityResponse = identityFeignClient.getUserCredentials(userName);

        List<String> owner = Arrays.stream(identityResponse.getRoles().split(",")).toList();

        if(owner.contains("ROLE_CUSTOMER") && owner.size() == 1) {
            throw new UserNotAutherizedException("You are UnAuthorized to get Orders from a shop...  "+identityResponse.getEmailId());
        }
        Shop shop = shopRepository.findByEmailId(identityResponse.getEmailId());
        if (shop == null) {
            throw new ShopIsNotFoundException("Please add your shop");
        }

        return orderFeignClient.getAllCustomersTotalAmountPerShopOrders(shop.getShopName());

    }
}
