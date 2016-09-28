package com.xuanru.dto.req.user;


import com.xuanru.dto.req.BaseReqDto;

public class LoginFormDto extends BaseReqDto {

	/**
	 * long:serialVersionUID
	 */
	private static final long serialVersionUID = -6410047172812621569L;

	private String username;

	private String pwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
