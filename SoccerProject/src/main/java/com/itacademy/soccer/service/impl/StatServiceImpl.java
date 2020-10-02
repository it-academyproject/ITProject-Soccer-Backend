package com.itacademy.soccer.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.itacademy.soccer.service.IStatService;

@Service
public class StatServiceImpl implements IStatService {
	
/*	@Override
	public Date initDateInterval(Date today, Long days) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(today);		
		c.add(Calendar.DATE, (int) -days);		

		return  c.getTime();
	}*/

	@Override	
	public Date initDateInterval(Long id) {
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);		
		c.add(Calendar.DATE, (int) -id);		

		return  c.getTime();
	
	}

}
