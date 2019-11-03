package cn.ep.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    //对象转字符串
    public static <T> String obj2String(T obj) throws JsonProcessingException {
        return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
    }

    //字符串转对象
    public static <T> T string2Obj(String str,Class<T> clazz) throws IOException {
        return clazz.equals(String.class)? (T) str :objectMapper.readValue(str,clazz);
    }

    // object转换为自定义对象
    public static <T> T obj2Class (Object obj,Class<T> clazz) throws IOException {
        return string2Obj(obj2String(obj), clazz);
    }

    public static <T> String parseObject(T obj) {
        return  obj instanceof String ? (String) obj : JSONObject.toJSONString(obj);
    }


    //字符串转对象
    public static <T> T parseObject(String str,Class<T> clazz) {
        return clazz.equals(String.class)? (T) str :JSONObject.parseObject(str,clazz);
    }

    // object转换为自定义对象
    public static <T> T parseObject (Object obj,Class<T> clazz){
        return parseObject(parseObject(obj), clazz);
    }

}