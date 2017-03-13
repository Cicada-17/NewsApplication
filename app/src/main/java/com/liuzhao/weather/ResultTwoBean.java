package com.liuzhao.weather;

import java.util.List;

/**
 * Created by Administrator on 2月17日0017.
 */

public class ResultTwoBean {
    private  LocationBean location;
    private List<Daily> daily;

    public ResultTwoBean(LocationBean location, List<Daily> daily, String last_update) {
        this.location = location;
        this.daily = daily;
        this.last_update = last_update;
    }

    public String getLast_update() {

        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    private  String last_update;
}
