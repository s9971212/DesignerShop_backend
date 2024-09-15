package com.designershop.utils;

import com.designershop.entities.Order;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.OrderException;
import com.designershop.exceptions.UserException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public final class FormatUtil {

    public static String userIdGenerate(UserProfile userProfile) throws UserException {
        String currentDate = DateTimeFormatUtil.currentLocalDateFormat(DateTimeFormatUtil.SIMPLE_YEAR_MONTH)
                .substring(1);

        String maxUserId = "00000";
        if (Objects.nonNull(userProfile) && StringUtils.equals(currentDate, userProfile.getUserId().substring(2, 7))) {
            maxUserId = userProfile.getUserId().substring(7, 12);
        }

        if (StringUtils.equals("99999", maxUserId)) {
            throw new UserException("本月註冊會員人數已滿，請洽客服人員");
        }

        int max = Integer.parseInt(maxUserId);
        String formatMax = String.format("%05d", ++max);
        return String.join("", "DU", currentDate, formatMax);
    }

    public static String orderIdGenerate(Order order) throws OrderException {
        String currentDate = DateTimeFormatUtil.currentLocalDateFormat(DateTimeFormatUtil.SIMPLE_DATE)
                .substring(1);

        String maxOrderId = "00000";
        if (Objects.nonNull(order) && StringUtils.equals(currentDate, order.getOrderId().substring(2, 9))) {
            maxOrderId = order.getUserId().substring(9, 14);
        }

        if (StringUtils.equals("99999", maxOrderId)) {
            throw new OrderException("本日訂單已滿，請洽客服人員");
        }

        int max = Integer.parseInt(maxOrderId);
        String formatMax = String.format("%05d", ++max);
        return String.join("", "DO", currentDate, formatMax);
    }
}
