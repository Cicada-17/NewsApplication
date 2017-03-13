package com.liuzhao.weather;

/**
 * Created by Administrator on 2月17日0017.
 */

public class LocationBean {
    private String  id ;
    private String   name;
    private String   country;
    private String   timezone;
    private String   timezone_offset;

    public LocationBean(String id, String name, String country, String timezone, String timezone_offset) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(String timezone_offset) {
        this.timezone_offset = timezone_offset;
    }
}

