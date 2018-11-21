package com.audit.modules.common.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * created on 14-11-17
 *
 * @author 王松
 */
public class JsonUtil {

    /**将字符串转换成java对象*/
    private static final ObjectMapper generatorMapper = new ObjectMapper();
    /**将java对象转换成字符串*/
    private static final ObjectMapper parserMapper = new ObjectMapper();
    
    /**将java对象转换成字符串(包含空值的情况)*/
    private static final ObjectMapper parserMapperAll = new ObjectMapper();

    /**设置java对象转换成json字符串的细粒度控制*/
    static {
        /**设置java对象转换成json字符串的细粒度控制*/
        generatorMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)  //获取字段
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)    //不准获取isXxx方法
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)       //不准获取getter方法
                .setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE)      //不准获取构造函数和static方法
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)                      //不准获取空值
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));                  //设置日期格式
        /**设置json字符串转换成java对象的细粒度控制*/
        parserMapper
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)                           //允许字符串中存在注释
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)               //允许无引号的键
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)                      //允许单引号
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)             //允许存在换行符等特殊字符
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)          //允许字符串中出现Bean中没有的属性
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))                   //设置日期格式
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)         //遇到空值时不抛出异常
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);        //如果列表中只有一条记录，依然处理成列表
        
        /**设置java对象转换成json字符串的细粒度控制(包含空值的情况)*/
        parserMapperAll.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)  //获取字段
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)    //不准获取isXxx方法
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)       //不准获取getter方法
                .setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE)      //不准获取构造函数和static方法
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));                  //设置日期格式
    }

    /**
     * 将json字符串转换成对象
     * @param json json字符串
     * @param c 对象的类型
     * @return
     */
    public static <T> T valueOf(String json, Class<T> c){
        T result = null;
        try {
            result = valueOf(parserMapper.getFactory().createParser(json), c);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
    private static <T> T valueOf(JsonParser parser, Class<T> c) throws IOException{
        T result = parser.readValueAs(c);
        parser.close();
        return result;
    }

    /**
     * 将json字符串转换成对象
     * @param reader 文件读取器
     * @param c 对象的类型
     * @return
     */
    public static <T> T valueOf(Reader reader, Class<T> c) throws IOException {
        return valueOf(parserMapper.getFactory().createParser(reader), c);
    }

    public static <T> List<T> valueOfList(Reader reader, Class<T> c) throws IOException {
        JavaType type = parserMapper.getTypeFactory().constructParametricType(ArrayList.class, c);
        List<T> result = parserMapper.readValue(reader, type);
        reader.close();
        return result;
    }

    /**
     * 将json字符串转换成指定类型的列表
     * @param json
     * @param c
     * @param <T>
     * @return
     */
    public static <T> List<T> valueOfList(String json, Class<T> c) {
        JavaType type = parserMapper.getTypeFactory().constructParametricType(ArrayList.class, c);
        try {
            return parserMapper.readValue(json, type);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 将json字符串转换成指定类型的Map
     * @param json 字符串
     * @param keyClass key的class
     * @param valueClass value的class
     * @param <T> key泛型
     * @param <V> value泛型
     * @return
     */
    public static <T, V> Map<T, V> valueOfMap(String json, Class<T> keyClass, Class<V> valueClass){
        JavaType type = parserMapper.getTypeFactory().constructParametricType(LinkedHashMap.class, keyClass, valueClass);
        try {
            return parserMapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> valueOfMap(Reader reader){
        return valueOfMap(reader, String.class, Object.class);
    }

    public static Map<String, Object> valueOfMap(String json){
        return valueOfMap(json, String.class, Object.class);
    }

    /**
     * 将json流转换成指定类型的Map
     * @param reader json流
     * @param keyClass key的class
     * @param valueClass value的class
     * @param <T> key泛型
     * @param <V> value泛型
     * @return
     */
    public static <T, V> Map<T, V> valueOfMap(Reader reader, Class<T> keyClass, Class<V> valueClass) {
        JavaType type = parserMapper.getTypeFactory().constructParametricType(HashMap.class, keyClass, valueClass);
        try {
            return parserMapper.readValue(reader, type);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 将java对象转换成json字符串
     * @param data 实际数据
     * @param <T> 泛型
     * @return
     */
    public static <T> String toJson(T data){
        if(data == null){
            return "";
        }
        String json = "";
        try {
            json = generatorMapper.writeValueAsString(data);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return json;
    }
    
    /**
     * 
     * 将java对象转换成json字符串（包含空值）
     * @param data 实际数据
     * @param <T> 泛型
     * @return 
     */
    public static <T> String toJsonAll(T data){
        if(data == null){
            return "";
        }
        String json = "";
        try {
            json = parserMapperAll.writeValueAsString(data);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return json;
    }

    /**
     * 将对象的所有字段都输出
     * 该方法只能使用于单元测试
     * @param data
     * @param <T>
     * @return
     */
    public static <T> String allToJson(T data){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)           //获取字段
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)    //不准获取isXxx方法
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)       //不准获取getter方法
                .setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE)      //不准获取构造函数和static方法
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)                      //不准获取空值
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));                  //设置日期格式
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
