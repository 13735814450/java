package com.jim.shirotest.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = ShiroProperties.PREFIX)
public class ShiroProperties {

	public static final String PREFIX = "chaos";

	private Boolean kaptchaOpen = false;

	private Boolean swaggerOpen = false;

	private String fileUploadPath;

	private Boolean haveCreatePath = false;

	private Boolean springSessionOpen = false;

	private Boolean production = false;

	/**
	 * session 失效时间（默认为30分钟 单位：秒）
	 */
	private Integer sessionInvalidateTime = 30 * 60;

	/**
	 * session 验证失效时间（默认为15分钟 单位：秒）
	 */
	private Integer sessionValidationInterval = 15 * 60;



	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public Boolean getKaptchaOpen() {
		return kaptchaOpen;
	}

	public void setKaptchaOpen(Boolean kaptchaOpen) {
		this.kaptchaOpen = kaptchaOpen;
	}

	public Boolean getSwaggerOpen() {
		return swaggerOpen;
	}

	public void setSwaggerOpen(Boolean swaggerOpen) {
		this.swaggerOpen = swaggerOpen;
	}

	public Boolean getSpringSessionOpen() {
		return springSessionOpen;
	}

	public void setSpringSessionOpen(Boolean springSessionOpen) {
		this.springSessionOpen = springSessionOpen;
	}

	public Integer getSessionInvalidateTime() {
		return sessionInvalidateTime;
	}

	public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
		this.sessionInvalidateTime = sessionInvalidateTime;
	}

	public Integer getSessionValidationInterval() {
		return sessionValidationInterval;
	}

	public void setSessionValidationInterval(Integer sessionValidationInterval) {
		this.sessionValidationInterval = sessionValidationInterval;
	}

	public Boolean getProduction() {
		return production;
	}

	public void setProduction(Boolean production) {
		this.production = production;
	}
}
