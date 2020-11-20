package com.itacademy.soccer.dto.weather;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"time",
"main",
"weather"
})
public class Time {


private String time;
public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}

@JsonProperty("main")
private  Main main;
@JsonProperty("weather")
private Weather weather;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("main")
public Main getMain() {
return main;
}

@JsonProperty("main")
public void setMain(Main main) {
this.main = main;
}

@JsonProperty("weather")
public Weather getWeather() {
return weather;
}

@JsonProperty("weather")
public void setWeather(Weather weather) {
this.weather = weather;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}