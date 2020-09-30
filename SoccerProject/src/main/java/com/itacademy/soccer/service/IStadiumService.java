package com.itacademy.soccer.service;


import com.itacademy.soccer.dao.IStadiumDAO;
import com.itacademy.soccer.dto.Stadium;
import org.springframework.stereotype.Service;

@Service
public interface IStadiumService extends IStadiumDAO {

    Stadium findByStadiumId(Long stadiumId);

}
