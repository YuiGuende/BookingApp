package com.example.demo.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.VNpayConfig;
import com.example.demo.dto.order.PaymentDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.booking.BookingStatus;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.payment.PaymentStatus;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.VNPayServiceInteface;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class VNPayService implements VNPayServiceInteface {

    private final VNpayConfig config = new VNpayConfig();
    private final PaymentService paymentService;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public VNPayService(PaymentService paymentService,
            BookingService bookingService,
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String createPaymentURL(PaymentDTO paymentDTO, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (paymentDTO.getAmount()) * 100;
        String bankCode = paymentDTO.getBankCode();

        String vnp_TxnRef = config.getRandomNumber(8);
        String vnp_IpAddr = config.getIpAddress(httpServletRequest);

        String vnp_TmnCode = config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = httpServletRequest.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = config.hmacSHA512(config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        paymentService.savePayment(
                new Payment(
                        "TT",
                        LocalDate.now(),
                        amount,
                        bookingService.findBookingById(paymentDTO.getBookingId()),
                        vnp_TxnRef,
                        PaymentStatus.PENDING)
        );
        return config.vnp_PayUrl + "?" + queryUrl;
    }

    @Override
    public ApiResponse<String> verifyPayment(Map<String, String> requestParams) {
        try {
            // Lấy secure hash từ request mà lười quá =))
            // String vnp_SecureHash = requestParams.get("vnp_SecureHash");
            // if (requestParams.containsKey("vnp_SecureHashType")) {
            //     requestParams.remove("vnp_SecureHashType");
            // }
            // if (requestParams.containsKey("vnp_SecureHash")) {
            //     requestParams.remove("vnp_SecureHash");
            // }
            String vnp_TxnRef = requestParams.get("vnp_TxnRef");
            Optional<Payment> paymetOptional = paymentRepository.findByVnp(vnp_TxnRef);
            if (paymetOptional.isEmpty()) {
                throw new ResourceNotFoundException("vnp_TxnRef not found!");
            }
            if(paymetOptional.get().getPaymentStatus()==PaymentStatus.CONFIRMED){
                return new ApiResponse<>("checked", "Thanh toán này đã hoàn thành trước đó", null);
            }

            boolean isPaymentValid = false;
            String vnp_ResponseCode = requestParams.get("vnp_ResponseCode");
            Payment payment = new Payment();
            if ("00".equals(vnp_ResponseCode)) {

                payment = paymentService.updatePaymentStatus(vnp_TxnRef, PaymentStatus.CONFIRMED);
                payment.getBooking().setStatus(BookingStatus.CONFIRMED);
                isPaymentValid = true;
                bookingRepository.save(payment.getBooking());
            } else {
                System.out.println("Verifyyyyyyyyyyyyyy cancellll");
                payment.setPaymentStatus(PaymentStatus.CANCELED);
                payment.getBooking().setStatus(BookingStatus.CANCELED);
                paymentService.savePayment(payment);
                bookingRepository.save(payment.getBooking());
            }
            if (isPaymentValid) {
                return new ApiResponse<>("success", "Thanh toán thành công", null);
            } else {
                return new ApiResponse<>("error", "Thanh toán không thành công", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>("error", "Lỗi xử lý thanh toán: " + e.getMessage(), null);
        }
    }

    @Override
    public String processIpnRequest(HttpServletRequest request) {
        try {
            // Lấy tất cả các tham số từ request
            Map<String, String> vnp_Params = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();

            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                if (paramValue != null && !paramValue.isEmpty()) {
                    vnp_Params.put(paramName, paramValue);
                }
            }

            // Xóa các tham số hash
            String vnp_SecureHash = vnp_Params.get("vnp_SecureHash");
            vnp_Params.remove("vnp_SecureHash");
            vnp_Params.remove("vnp_SecureHashType");

            // Kiểm tra checksum
            String secureHash = config.hashAllFields(vnp_Params);

            // So sánh secure hash
            if (secureHash.equals(vnp_SecureHash)) {
                // Kiểm tra mã đơn hàng trong database
                String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
                boolean orderExists = paymentService.isVNPaymentExist(vnp_TxnRef);

                if (orderExists) {
                    // Kiểm tra số tiền
                    String vnp_Amount = vnp_Params.get("vnp_Amount");
                    boolean validAmount = paymentService.checkAmount(vnp_TxnRef, vnp_Amount);
                    // boolean validAmount = bookingService.checkAmount(vnp_TxnRef, vnp_Amount);

                    if (validAmount) {
                        // Kiểm tra trạng thái đơn hàng
                        boolean orderNotProcessed = true; // Thay bằng logic kiểm tra trạng thái đơn hàng
                        // boolean orderNotProcessed = bookingService.checkOrderStatus(vnp_TxnRef);

                        if (orderNotProcessed) {
                            // Kiểm tra mã phản hồi
                            String vnp_ResponseCode = vnp_Params.get("vnp_ResponseCode");
                            if ("00".equals(vnp_ResponseCode)) {
                                // Giao dịch thành công, cập nhật database
                                paymentService.updatePaymentStatus(vnp_TxnRef, PaymentStatus.CONFIRMED);
                            } else {
                                // Giao dịch thất bại, cập nhật database
                                System.out.println("INPnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn cancellll");
                                paymentService.updatePaymentStatus(vnp_TxnRef, PaymentStatus.CANCELED);
                            }

                            // Phản hồi thành công
                            return "{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}";
                        } else {
                            // Đơn hàng đã được xử lý
                            return "{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}";
                        }
                    } else {
                        // Số tiền không hợp lệ
                        return "{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}";
                    }
                } else {
                    // Không tìm thấy đơn hàng
                    return "{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}";
                }
            } else {
                // Chữ ký không hợp lệ
                return "{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"RspCode\":\"99\",\"Message\":\"Unknown error\"}";
        }
    }

}
