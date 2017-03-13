package com.liuzhao.Bean;

/**
 * Created by Administrator on 2月16日0016.
 */

public class Person {
    private  String id;
    private  String nobi;
    private  String email;
    private  String username;
    private String logo_url;
    public Person(){}
    public Person(String id, String nobi, String email, String username, String logo_url) {
        this.id = id;
        this.nobi = nobi;
        this.email = email;
        this.username = username;
        this.logo_url = logo_url;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNobi() {
        return nobi;
    }

    public void setNobi(String nobi) {
        this.nobi = nobi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }


}
