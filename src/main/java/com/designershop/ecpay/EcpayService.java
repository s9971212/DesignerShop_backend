package com.designershop.ecpay;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EcpayService {

    private final String FROM = "DesignerShop";

    public String ecpayCheckout(String orderId, String createdDate, String totalPrice, String productNames) {
        AllInOne all = new AllInOne("");

        AioCheckOutALL checkOut = new AioCheckOutALL();
        // 僅支援英數字(測試時使用UUID避免訂單編號重覆導致交易失敗)
        checkOut.setMerchantTradeNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20));
        // 僅支援yyyy/MM/dd HH:mm:ss格式
        checkOut.setMerchantTradeDate(createdDate);
        // 僅支援正整數(int)
        checkOut.setTotalAmount(totalPrice);
        // 交易描述
        checkOut.setTradeDesc(FROM);
        // 多個物品需用#分隔(item1#item2)
        checkOut.setItemName(productNames);
        // 以Post傳送付款結果至Server網址
        checkOut.setReturnURL("<http://localhost:8080/api/orders/>");
        // 傳送付款結果並將頁面導至自製頁面網址(OrderResultURL與ClientBackURL同時存在將以OrderResultURL為主)
        checkOut.setOrderResultURL("<http://>");
        // 將頁面導至自製頁面網址(不傳送付款結果，OrderResultURL與ClientBackURL同時存在將以OrderResultURL為主)
        // checkOut.setClientBackURL("<http://>");
        // 是否需要額外的付款資訊(Y/N)
        checkOut.setNeedExtraPaidInfo("N");

        return all.aioCheckOut(checkOut, null);
    }
}
