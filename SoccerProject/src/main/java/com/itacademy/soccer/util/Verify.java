package com.itacademy.soccer.util;

import java.util.Collection;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.itacademy.soccer.dto.Team;

public class Verify {

// DATES ->

	public static boolean notNullEquals(Date toVerify, Date toCompare) {
		return (toVerify == null || toVerify.equals(toCompare)) ? false : true;
	}

	public static boolean notNullEqualsBefore(Date toVerify, Date toCompare) {
		return (toVerify == null || toVerify.equals(toCompare) || toVerify.before(toCompare)) ? false : true;
	}

	public static boolean isExpired(Date toVerify) {
		return (toVerify != null && toVerify.before(new Date())) ? true : false;
	}

	public static boolean inTwoMinToExpire(Date toVerify, Date toCompare) {
		return (toVerify != null && toCompare != null && toCompare.after(toVerify))
				&& new Date(toCompare.getTime() - 120000).before(toVerify) ? true : false;
	}

	public static boolean notNullAfter3Days(Date toVerify) {
		return (toVerify == null || toVerify.after(new Date(System.currentTimeMillis() + 259_080_000))) ? false : true;
	}

// STRING ->

	public static boolean notNullEmptyEquals(String toVerify, String toCompare) {
		return (!StringUtils.hasLength(toVerify) || toVerify.equals(toCompare)) ? false : true;
	}

	public static boolean notNullEmpty(String toVerify) {
		return (toVerify != null && !toVerify.isEmpty()) ? true : false;
	}

// COLLECTIONS ->

	public static boolean isNullEmpty(Collection<?> toVerify) {
		return (toVerify == null || toVerify.isEmpty()) ? true : false;
	}

// TEAMS ->

	public static boolean isNullEmptyEquals(Team toVerify, Team toCompare) {
		return (toVerify == null || toVerify.equals(toCompare) || toVerify.hashCode() == toCompare.hashCode()) 
				? true : false;
	}

// NUMBERS ->

	public static boolean notNullZero(Number toVerify) {
		return (toVerify.floatValue() > 0) ? true : false;
	}

	public static boolean notLessEqZeroEquals(Number toVerify, Number toCompare) {
		return (toVerify.floatValue() <= 0 || toVerify.floatValue() == toCompare.floatValue()) 
				? false : true;
	}

	public static boolean isPercentHigher(Number toVerify, Number toCompare, float percent) {
		return (toVerify.floatValue() > 0 && toCompare.floatValue() > 0 && percent > 0
				&& toVerify.floatValue() >= (toCompare.floatValue() * percent)) ? true : false;
	}

}
