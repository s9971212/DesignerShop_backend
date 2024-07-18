package com.designershop.utils;

import org.apache.commons.lang3.StringUtils;

import com.designershop.exceptions.UserException;

public final class FormatUtil {

	public static String userIdGenerate(String maxUserId) throws UserException {
		if (StringUtils.equals("99999", maxUserId)) {
			throw new UserException("本月註冊會員人數已滿，請洽客服人員");
		}

		if (StringUtils.isBlank(maxUserId)) {
			maxUserId = "00000";
		}

		String currentDate = DateTimeFormatUtil.currentLocalDateFormat();
		int max = Integer.parseInt(maxUserId);
		String formatMax = String.format("%05d", ++max);
		return currentDate.substring(1).concat(formatMax);
	}
}