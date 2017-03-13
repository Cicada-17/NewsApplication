package com.liuzhao.Bean;

/**
 * Created by Administrator on 2月19日0019.
 */

public class RoomBean {
    private  String room_id;
    private String  tag_name;
    private String  room_src;
    private String  is_vertical;
    private String vertical_src ;
    private String  room_name;
    private String  avatar;
    private String  notice;
    private String show_status ;
    private String  online;
    private  String nickname ;
    private  String  hls_url;
    private  String  is_pass_player;
    private  String  is_ticket;
    private  String  show_details;
    public  RoomBean (){}

    public RoomBean(String room_id, String tag_name, String room_src, String is_vertical, String vertical_src, String room_name, String avatar, String notice, String show_status, String online, String nickname, String hls_url, String is_pass_player, String is_ticket, String show_details) {
        this.room_id = room_id;
        this.tag_name = tag_name;
        this.room_src = room_src;
        this.is_vertical = is_vertical;
        this.vertical_src = vertical_src;
        this.room_name = room_name;
        this.avatar = avatar;
        this.notice = notice;
        this.show_status = show_status;
        this.online = online;
        this.nickname = nickname;
        this.hls_url = hls_url;
        this.is_pass_player = is_pass_player;
        this.is_ticket = is_ticket;
        this.show_details = show_details;
    }

    public String getRoom_id() {

        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getRoom_src() {
        return room_src;
    }

    public void setRoom_src(String room_src) {
        this.room_src = room_src;
    }

    public String getIs_vertical() {
        return is_vertical;
    }

    public void setIs_vertical(String is_vertical) {
        this.is_vertical = is_vertical;
    }

    public String getVertical_src() {
        return vertical_src;
    }

    public void setVertical_src(String vertical_src) {
        this.vertical_src = vertical_src;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getShow_status() {
        return show_status;
    }

    public void setShow_status(String show_status) {
        this.show_status = show_status;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHls_url() {
        return hls_url;
    }

    public void setHls_url(String hls_url) {
        this.hls_url = hls_url;
    }

    public String getIs_pass_player() {
        return is_pass_player;
    }

    public void setIs_pass_player(String is_pass_player) {
        this.is_pass_player = is_pass_player;
    }

    public String getIs_ticket() {
        return is_ticket;
    }

    public void setIs_ticket(String is_ticket) {
        this.is_ticket = is_ticket;
    }

    public String getShow_details() {
        return show_details;
    }

    public void setShow_details(String show_details) {
        this.show_details = show_details;
    }
}
