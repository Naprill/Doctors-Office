package com.chernivtsi.doctorsoffice.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonSerialize(using = ToStringSerializer.class)
public enum Interval {

    BUSY("Зайнято"), FREE("Вільно");

    private String intervalType;

    Interval(String type) {
        intervalType = type;
    }

    @JsonValue
    public String value() { return this.intervalType; }

    public String getIntervalType() {
        return intervalType;
    }

    @Override
    public String toString() {
        return intervalType;
    }
}
