package com.bh.live.common.constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnumDesc implements Serializable{


	private String name;

	private List<Map<String, Object>> constants = new ArrayList<>();
	
	

	public EnumDesc(String name, List<Map<String, Object>> constants) {
		super();
		this.name = name;
		this.constants = constants;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getConstants() {
		return constants;
	}

	public void setConstants(List<Map<String, Object>> constants) {
		this.constants = constants;
	}

}
