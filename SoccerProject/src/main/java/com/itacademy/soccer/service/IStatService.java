package com.itacademy.soccer.service;

import java.util.*;

import com.itacademy.soccer.dto.Sale;

public interface IStatService {

	Date initDateInterval(Long id);

	List<Sale> getSalesStatsKO(List<Sale> allSalesPeriod);

	HashMap<Object, Object> getSalesStatsOK(List<Sale> allSalesPeriod);

	HashMap<Object, Object> getAverageBidsperSales(List<Sale> allSales);


}
