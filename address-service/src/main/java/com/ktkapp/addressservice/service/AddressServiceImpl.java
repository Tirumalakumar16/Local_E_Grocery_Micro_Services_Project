package com.ktkapp.addressservice.service;

import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.ktkapp.addressservice.models.Address;
import com.ktkapp.addressservice.repository.AddressRepo;
import com.ktkapp.addressservice.service.kafka.KafkaPublisher;
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

    private KafkaPublisher kafkaPublisher;
    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo, ModelMapper modelMapper, KafkaPublisher kafkaPublisher) {
        this.addressRepo = addressRepo;
        this.modelMapper = modelMapper;
        this.kafkaPublisher = kafkaPublisher;
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
        ResponseAddressDto responseAddressDto = modelMapper.map(address, ResponseAddressDto.class);
   //   kafkaPublisher.sendMessage("Address saved successfully for email : "+ requestAddressDto.getEmailRequest().getEmailId());
        return responseAddressDto;
    }

    @Override
    public List<ResponseAddressDto> getAddress(String email) throws AddressNotFoundWithEmail {

        List<Address> address = addressRepo.findAllByEmailId(email);
        if(address == null) {
            throw new AddressNotFoundWithEmail("Address is not found with provided email "+email);
        }

        List<ResponseAddressDto> responseAddressDtos = Arrays.asList(modelMapper.map(address, ResponseAddressDto[].class));
     //   kafkaPublisher.sendMessage("Retrieved the Addresses from Address service with email Id : "+email);
        return responseAddressDtos;
    }

    @Override
    public String deleteAddress(DeleteAddressRequest deleteAddress) {
         addressRepo.deleteByZipAndEmailId(deleteAddress.getZip(),deleteAddress.getEmailAddressRequest().getEmailId(),deleteAddress.getHouseNumber());
      //   kafkaPublisher.sendMessage("Address is deleted from your account...");
         return "Address Deleted successfully from your Account.... ";
    }

    @Override
    public ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest) {

        Address address = addressRepo.findByZipAndEmailId(updateAddressRequest.getZip(),updateAddressRequest.getEmailRequest().getEmailId());

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
    public ResponseAddressDto getByZipAddress(String email, String houseNumber,String zip) {

        Address address= addressRepo.findByEmailIdAndHouseNumberAndZip(email,houseNumber,zip);
     //   kafkaPublisher.sendMessage("Address is fetched :: " +email);
    return modelMapper.map(address, ResponseAddressDto.class);

    }

    @Override
    public ResponseAddressDto getByHouseNumber(String houseNumber) {

        Address address = addressRepo.findByHouseNumber(houseNumber);

        return modelMapper.map(address, ResponseAddressDto.class);
    }
}
