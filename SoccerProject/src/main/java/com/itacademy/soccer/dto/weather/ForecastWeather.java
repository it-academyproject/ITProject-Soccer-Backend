package com.itacademy.soccer.dto.weather;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"date",
"time"	
})

public class ForecastWeather {

private Time time;


public Time getTime() {
	return time;
}

public void setTime(Time time) {
	this.time = time;
}


@JsonProperty("date")
private String date;
@JsonIgnore
private Serializable additionalProperties = new HashMap<String, Object>();


@JsonProperty("date")
public String getdate() {
return date;
}


@JsonProperty("date")
public void setdate(String date) {
this.date= date;
}

@SuppressWarnings("unchecked")
@JsonAnyGetter
public Map<Date , Time> getAdditionalProperties() {
return (Map<Date, Time>) this.additionalProperties;
}

@SuppressWarnings("unchecked")
@JsonAnySetter
public void setAdditionalProperty(Object date, Object time ) {
((HashMap<Object, Object>) this.additionalProperties).put(date,time);
}

@Override
public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append(getdate() );
	return buffer.toString();
}

}