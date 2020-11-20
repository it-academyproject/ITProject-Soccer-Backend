package com.itacademy.soccer.controller;

import com.itacademy.soccer.weather.WeatherService;
import com.itacademy.soccer.dto.weather.Example;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //introducir el nombre de la ciudad, devuelve el tiempo del futuro a la hora deseada en el servicio
	//cada period es un intervalo de 3 horas
	@GetMapping("forecast/{city}/{periods}")
	public Example getWeatherForFive(
			@PathVariable String city, @PathVariable int periods) throws ParseException, JSONException {
		return this.weatherService.getWeatherForFive(city,periods);
	}	

	//introducir el nombre de la ciudad, devuelve tiempo actual
	@GetMapping("weather/{city}")
	public List<Example> getWeather(
			@PathVariable String city) throws JSONException {
		return this.weatherService.getWeather(city);		
	}
	
	
	
}
