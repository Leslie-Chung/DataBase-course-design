package com.onlineclass.pojo;

/**
 * 
 * @author indext
 * @since 2019.10.10
 * @version 1.0
 */
public class User extends OnlineClassObject {

	/**
	 * 
	 */
	private static long serialVersionUID = -1710406289773679064L;

	private String user_name;// 登录用户名
	private String user_password;// 登录密码
	private String user_type;// 登录用户类型

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static void setSerialVersionUID(long serialVersionUID) {
		User.serialVersionUID = serialVersionUID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	@Override
	public String toString() {
		return "User [user_name=" + user_name + ", user_password=" + user_password
				+ ", user_type=" + user_type + "]";
	}

	public User(String user_name, String user_password, String user_type) {
		super();
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_type = user_type;
	}

	public User() {

	}

}
