/**
 * 
 */
package com.bh.live.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * jsonson util
 *
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	
	static {

	}
	
	/**
	 * Java对象转换为json
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String obj2json(Object obj){
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转换为Java对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T json2obj(String jsonStr, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转换为map
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) {
		try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转换为指定value类型map
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz){
		try {
			Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
			});
			Map<String, T> result = new HashMap<String, T>();
			for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
				result.put(entry.getKey(), map2obj(entry.getValue(), clazz));
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public static  Map<byte[], byte[]> readJsonByteMap(String json){
    	Map<String, Object> map = json2map(json);
        Map<byte[], byte[]> vmap = new HashMap<>();
        for(String key:map.keySet()){
            vmap.put(key.getBytes(),map.get(key).toString().getBytes());
        }
        return vmap;
    }
	

	/**
	 * json转换为List
	 * 
	 * @param jsonArrayStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
		try {
			List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
			});
			List<T> result = new ArrayList<T>();
			for (Map<String, Object> map : list) {
				result.add(map2obj(map, clazz));
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * map转换为Java对象
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T map2obj(Map map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}

	/**
	 * xml转换为json
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String xml2json(String xml){
		try {
			StringWriter w = new StringWriter();
			JsonParser jp = null;
			JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
			while (jp.nextToken() != null) {
				jg.copyCurrentEvent(jp);
			}
			jp.close();
			jg.close();
			return w.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
