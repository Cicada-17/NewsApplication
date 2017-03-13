package com.liuzhao.Bean;

public class RoomItemBean {
	private String   room_id;
	private String   room_src;
	private String  vertical_src ;
	private String   isVertical;
	private String   cate_id;
	private String  room_name ;
	private String  owner_uid ;
	private String   nickname;
	private String   online;
	private String   url;
	private String   game_url;
	private String   game_name;
	private String   avatar;
	@Override
	public String toString() {
		return "RoomItemBean [room_id=" + room_id + ", room_src=" + room_src + ", vertical_src=" + vertical_src
				+ ", isVertical=" + isVertical + ", cate_id=" + cate_id + ", room_name=" + room_name + ", owner_uid="
				+ owner_uid + ", nickname=" + nickname + ", online=" + online + ", url=" + url + ", game_url="
				+ game_url + ", game_name=" + game_name + ", avatar=" + avatar + ", avatar_mid=" + avatar_mid
				+ ", avatar_small=" + avatar_small + ", jumpUrl=" + jumpUrl + ", icon_data=" + icon_data + "]";
	}
	private String   avatar_mid;
	private String   avatar_small;
	private String   jumpUrl;
	private IcondataBean icon_data;
	public RoomItemBean(String room_id, String room_src, String vertical_src, String isVertical, String cate_id,
			String room_name, String owner_uid, String nickname, String online, String url, String game_url,
			String game_name, String avatar, String avatar_mid, String avatar_small, String jumpUrl,
			IcondataBean icon_data) {
		super();
		this.room_id = room_id;
		this.room_src = room_src;
		this.vertical_src = vertical_src;
		this.isVertical = isVertical;
		this.cate_id = cate_id;
		this.room_name = room_name;
		this.owner_uid = owner_uid;
		this.nickname = nickname;
		this.online = online;
		this.url = url;
		this.game_url = game_url;
		this.game_name = game_name;
		this.avatar = avatar;
		this.avatar_mid = avatar_mid;
		this.avatar_small = avatar_small;
		this.jumpUrl = jumpUrl;
		this.icon_data = icon_data;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_src() {
		return room_src;
	}
	public void setRoom_src(String room_src) {
		this.room_src = room_src;
	}
	public String getVertical_src() {
		return vertical_src;
	}
	public void setVertical_src(String vertical_src) {
		this.vertical_src = vertical_src;
	}
	public String getIsVertical() {
		return isVertical;
	}
	public void setIsVertical(String isVertical) {
		this.isVertical = isVertical;
	}
	public String getCate_id() {
		return cate_id;
	}
	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getOwner_uid() {
		return owner_uid;
	}
	public void setOwner_uid(String owner_uid) {
		this.owner_uid = owner_uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGame_url() {
		return game_url;
	}
	public void setGame_url(String game_url) {
		this.game_url = game_url;
	}
	public String getGame_name() {
		return game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getAvatar_mid() {
		return avatar_mid;
	}
	public void setAvatar_mid(String avatar_mid) {
		this.avatar_mid = avatar_mid;
	}
	public String getAvatar_small() {
		return avatar_small;
	}
	public void setAvatar_small(String avatar_small) {
		this.avatar_small = avatar_small;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	public IcondataBean getIcon_data() {
		return icon_data;
	}
	public void setIcon_data(IcondataBean icon_data) {
		this.icon_data = icon_data;
	}
	

}
