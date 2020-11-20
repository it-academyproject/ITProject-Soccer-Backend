package com.itacademy.soccer.dto.weather;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"time"
})
public class Date {

@JsonProperty("time")
private Time time;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();


@JsonProperty("time")
public Time gettime() {
return time;
}

@JsonProperty("time")
public void settime(Time time) {
this.time = time;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append(gettime());
	return buffer.toString();
}
}