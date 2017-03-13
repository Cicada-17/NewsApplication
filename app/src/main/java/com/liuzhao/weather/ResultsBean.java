package com.liuzhao.weather;

/**
 * Created by Administrator on 2月17日0017.
 */

public class ResultsBean {
    private LocationBean location;
    private NowBean now;
    private String last_update;

    public ResultsBean (){}
    public ResultsBean(LocationBean location, NowBean now, String last_update) {
        this.location = location;
        this.now = now;
        this.last_update = last_update;
    }

    public LocationBean getLocation() {

        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
