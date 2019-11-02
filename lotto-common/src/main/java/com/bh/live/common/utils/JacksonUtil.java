/**
 * 
 */
package com.bh.live.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
public class JacksonUtil {

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
	public static String obj2json(Object obj) throws Exception {
		return objectMapper.writeValueAsString(obj);
	}

	/**
	 * json转换为Java对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T json2obj(String jsonStr, Class<T> clazz) throws Exception {
		return objectMapper.readValue(jsonStr, clazz);
	}

	/**
	 * json转换为map
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) throws Exception {
		return objectMapper.readValue(jsonStr, Map.class);
	}

	/**
	 * json转换为指定value类型map
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) throws Exception {
		Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
		});
		Map<String, T> result = new HashMap<String, T>();
		for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
			result.put(entry.getKey(), map2obj(entry.getValue(), clazz));
		}
		return result;
	}
	
    public static  Map<byte[], byte[]> readJsonByteMap(String json) throws Exception {
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
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws Exception {
		List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
		});
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			result.add(map2obj(map, clazz));
		}
		return result;
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
	 * json转换为xml
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static String json2xml(String jsonStr) throws Exception {
		JsonNode root = objectMapper.readTree(jsonStr);
		String xml = "";
		return xml;
	}

	/**
	 * xml转换为json
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String xml2json(String xml) throws Exception {
		StringWriter w = new StringWriter();
		JsonParser jp = null;
		JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
		while (jp.nextToken() != null) {
			jg.copyCurrentEvent(jp);
		}
		jp.close();
		jg.close();
		return w.toString();
	}

	/**
	 * Java对象转换为xml
	 * 
	 * @param o
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String obj2xml(Object o) throws JsonProcessingException {
		return "";
	}

	/**
	 * xml转换为Java对象
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T xml2obj(String xml, Class<T> clazz) throws IOException {
		return null;
	}

}
