package com.ktkapp.addressservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktkapp.addressservice.config.KafkaPublisherClient;
import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.ktkapp.addressservice.models.Address;
import com.ktkapp.addressservice.repository.AddressRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepo addressRepo;
    private ModelMapper modelMapper;

    private ObjectMapper objectMapper;


    private KafkaPublisherClient kafkaPublisherClient;
    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo, ModelMapper modelMapper, ObjectMapper objectMapper, KafkaPublisherClient kafkaPublisherClient) {
        this.addressRepo = addressRepo;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.kafkaPublisherClient = kafkaPublisherClient;
    }

    @Override
    public ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto) {


        Address address = new Address();
        address.setCity(requestAddressDto.getCity());
        address.setDistrict(requestAddressDto.getDistrict());
        address.setState(requestAddressDto.getState());
        address.setZip(requestAddressDto.getZip());
        address.setHouseNumber(requestAddressDto.getHouseNumber());
        address.setEmailId(requestAddressDto.getEmailRequest().getEmailId());
        address.setLandMark(requestAddressDto.getLandMark());
        address.setStreetName(requestAddressDto.getStreetName());
        address.setCreatedAt(new Date());

        address =addressRepo.save(address);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setTo(requestAddressDto.getEmailRequest().getEmailId());
        kafkaMessage.setFrom("grocerystore4169@gmail.com");
        kafkaMessage.setSubject("New Address is Saved");
        kafkaMessage.setBody("New address is saved successfully , please confirm the details \n \n" +
                "House Number : " + requestAddressDto.getHouseNumber() +"\n"+
                "City : "+ requestAddressDto.getCity()+"\n"+
                "Land Mark : "+requestAddressDto.getLandMark() +"\n\n\n"+
                "Thanks , Team Grocery Store!!!"
        );
        try {
            kafkaPublisherClient.sendMessage("newAddress", objectMapper.writeValueAsString(kafkaMessage));
        } catch (Exception e ) {
            System.out.println("something Wrong in address service");
        }


        return modelMapper.map(address, ResponseAddressDto.class);
    }

    @Override
    public List<ResponseAddressDto> getAddress(String email) throws AddressNotFoundWithEmail {


        List<Address> address = addressRepo.findAllByEmailId(email);

        if(address.isEmpty()) {
            throw new AddressNotFoundWithEmail("Addresses is not found with provided email "+email);
        }

        //   kafkaPublisher.sendMessage("Retrieved the Addresses from Address service with email Id : "+email);
        return Arrays.asList(modelMapper.map(address, ResponseAddressDto[].class));
    }

    @Override
    public String deleteAddress(DeleteAddressRequest deleteAddress) throws AddressNotFoundWithEmail {
        Address address = addressRepo.findByZipAndEmailId(deleteAddress.getZip(),deleteAddress.getEmailAddressRequest().getEmailId());

        if(address ==  null) {
            throw new AddressNotFoundWithEmail("House Number is not existed for email "+deleteAddress.getEmailAddressRequest().getEmailId());
        }
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setTo(deleteAddress.getEmailAddressRequest().getEmailId());
        kafkaMessage.setFrom("grocerystore4169@gmail.com");
        kafkaMessage.setSubject("Address deleted");
        kafkaMessage.setBody("House Number : "+ deleteAddress.getHouseNumber()+" address is deleted successfully , please check if not deleted by you ....\n\n\n\n\n"+"Team Grocery Store!!!.");
         addressRepo.deleteByZipAndEmailId(deleteAddress.getZip(),deleteAddress.getEmailAddressRequest().getEmailId(),deleteAddress.getHouseNumber());

        try {
            kafkaPublisherClient.sendMessage("newAddress", objectMapper.writeValueAsString(kafkaMessage));
        } catch (Exception e ) {
            System.out.println("something Wrong in address service");
        }
      //   kafkaPublisher.sendMessage("Address is deleted from your account...");
         return "Address Deleted successfully from your Account.... ";
    }

    @Override
    public ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest) throws AddressNotFoundWithEmail {

        Address address = addressRepo.findByZipAndEmailId(updateAddressRequest.getZip(),updateAddressRequest.getEmailRequest().getEmailId());

        if(address ==  null) {
            throw new AddressNotFoundWithEmail("Address is not existed for email "+updateAddressRequest.getEmailRequest().getEmailId());
        }

        if(updateAddressRequest.getState() != null && updateAddressRequest.getState().length()>=3){

            address.setState(updateAddressRequest.getState());
        }
        if(updateAddressRequest.getDistrict() != null && updateAddressRequest.getDistrict().length()>=3){

            address.setDistrict(updateAddressRequest.getDistrict());
        }
        if(updateAddressRequest.getHouseNumber() != null && updateAddressRequest.getHouseNumber().length()>=3){

            address.setHouseNumber(updateAddressRequest.getHouseNumber());
        }
        if(updateAddressRequest.getLandMark() != null && updateAddressRequest.getLandMark().length()>=3){

            address.setLandMark(updateAddressRequest.getLandMark());
        }
        if(updateAddressRequest.getCity() != null && updateAddressRequest.getCity().length()>=3){

            address.setCity(updateAddressRequest.getCity());
        }
        if(updateAddressRequest.getStreetName() != null && updateAddressRequest.getStreetName().length()>=3){

            address.setStreetName(updateAddressRequest.getStreetName());
        }
        address.setEmailId(updateAddressRequest.getEmailRequest().getEmailId());

        address = addressRepo.save(address);

     //   kafkaPublisher.sendMessage("Address is updated successfully : "+address.getZip());

        return modelMapper.map(address, ResponseAddressDto.class);
    }


    @Override
    public ResponseAddressDto getByZipAddress(String email, String houseNumber,String zip) throws AddressNotFoundWithEmail {

        Address address= addressRepo.findByEmailIdAndHouseNumberAndZip(email,houseNumber,zip);

        if(address ==  null) {
            throw new AddressNotFoundWithEmail("House Number is not existed for email "+email);
        }
     //   kafkaPublisher.sendMessage("Address is fetched :: " +email);
    return modelMapper.map(address, ResponseAddressDto.class);

    }

    @Override
    public ResponseAddressDto getByHouseNumber(String houseNumber) throws AddressNotFoundWithEmail {

        Address address = addressRepo.findByHouseNumber(houseNumber);

        if(address ==  null) {
            throw new AddressNotFoundWithEmail("House Number is not existed for House Number "+ houseNumber);
        }

        return modelMapper.map(address, ResponseAddressDto.class);
    }
}
