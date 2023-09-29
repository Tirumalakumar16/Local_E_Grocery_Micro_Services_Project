package com.payment.PaymentService.service;

import com.payment.PaymentService.dtos.RequestPaymentDto;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;
import com.payment.PaymentService.models.Payment;
import com.payment.PaymentService.models.PaymentStatus;
import com.payment.PaymentService.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository paymentRepository;

    private ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponsePaymentDto pay(RequestPaymentDto requestpaymentDto) {
        Payment payment = new Payment();

        payment.setPaymentMode(requestpaymentDto.getPaymentMode());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTotalAmount(requestpaymentDto.getTotalAmount());

        String generateUUIDNo = String.format("%010d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
        String unique_no = generateUUIDNo.substring( generateUUIDNo.length() - 10);

        payment.setTransactionId("Tx"+unique_no);
        payment.setCustomerEmailId(requestpaymentDto.getCustomerEmailId());
        payment.setCustomerName(requestpaymentDto.getCustomerName());
        Payment payment1 = paymentRepository.save(payment);

        return modelMapper.map(payment1, ResponsePaymentDto.class);
    }


    @Override
    public List<ResponsePaymentDto> getAllPayments(String email) throws PaymentsNotFound {
        List<Payment> payments = paymentRepository.finByEmailId(email);

        if(payments.isEmpty()) {
            throw new PaymentsNotFound("No payments found by email = "+email);
        }
        return Arrays.asList(modelMapper.map(payments, ResponsePaymentDto[].class));
    }
}
