package com.designershop.ecpay;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EcpayService {

    private final String FROM = "DesignerShop";

    public String ecpayCheckout(String orderId, String createdDate, String totalPrice, String productNames) {
        AllInOne all = new AllInOne("");

        AioCheckOutALL checkOut = new AioCheckOutALL();
        // 測試時使用UUID避免訂單編號重覆導致交易失敗
        checkOut.setMerchantTradeNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20));
        checkOut.setMerchantTradeDate(createdDate);
        checkOut.setTotalAmount(totalPrice);
        checkOut.setTradeDesc(FROM);
        checkOut.setItemName(productNames);
        checkOut.setReturnURL("<http://211.23.128.214:5000>");
        checkOut.setClientBackURL("<http://localhost:8080/>");
        checkOut.setNeedExtraPaidInfo("N");
        checkOut.setRedeem("Y");

        return all.aioCheckOut(checkOut, null);
    }
}
