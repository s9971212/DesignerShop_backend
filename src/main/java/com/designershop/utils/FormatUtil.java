package com.designershop.utils;

import org.apache.commons.lang3.StringUtils;

import com.designershop.exceptions.UserException;

public final class FormatUtil {

	public static String userIdGenerate(String userId) throws UserException {
		String currentDate = DateTimeFormatUtil.currentLocalDateFormat().substring(1);

		String maxUserId = "00000";
		if (StringUtils.equals(currentDate, userId.substring(0, 5))) {
			maxUserId = userId.substring(5, 10);
		}

		if (StringUtils.equals("99999", maxUserId)) {
			throw new UserException("本月註冊會員人數已滿，請洽客服人員");
		}

		int max = Integer.parseInt(maxUserId);
		String formatMax = String.format("%05d", ++max);
		return currentDate.concat(formatMax);
	}
}
