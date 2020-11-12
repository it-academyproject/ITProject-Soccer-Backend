package com.itacademy.soccer.util;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.itacademy.soccer.dto.Team;

public class Verify {
	
	public static boolean isNotNullEmptyEquals(String toVerify, String toCompare) {
		return (!StringUtils.hasLength(toVerify) || toVerify.equals(toCompare)) ? false : true;
	}

	public static boolean isNotNullEmptyEquals(Date toVerify, Date toCompare) {
		return (toVerify == null || toVerify.equals(toCompare)) ? false : true;
	}

	public static boolean isNotNullEmptyEquals(Integer toVerify, Integer toCompare) {
		return (toVerify == null || toVerify.equals(toCompare)) ? false : true;
	}
	
	public static boolean isNullEmptyEquals(Team toVerify, Team toCompare) {
		return (toVerify.equals(null) || toVerify.equals(toCompare) || toVerify.hashCode() == toCompare.hashCode())
				? true : false;
	}

}
