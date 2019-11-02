package com.bh.live.common.constant;

public class EnumCategory {
	
	private String code;
	
	private String name;
	
	private Class<?> enumClass;
	

	public EnumCategory(String code, String name, Class<?> enumClass) {
		super();
		this.code = code;
		this.name = name;
		this.enumClass = enumClass;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(Class<?> enumClass) {
		this.enumClass = enumClass;
	}
	

}
