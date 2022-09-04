package com.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2021-03-26 16:13
 */
public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    public static String getValue(String str, String key){
        try {
            JSONObject jsonObject = JSONObject.parseObject(str);
            return jsonObject.get(key).toString();
        } catch (Exception e){
            logger.info("字符转json转换失败，格式错误！", e);
            return null;
        }
    }

    public static JSON obj2Json(Object object){
        try {
            JSON json = (JSON) JSONObject.toJSON(object);
            return json;
        } catch (Exception e){
            logger.info("对象转json转换失败，格式错误！", e);
            return null;
        }
    }


    public static JSONObject obj2JsonObject(Object object){
        try{
            JSONObject jsonObj = (JSONObject) JSONObject.toJSON(object);
            return jsonObj;
        } catch (Exception e){
            logger.info("对象转JsonObject转换失败，格式错误！", e);
            return null;
        }
    }

    public static String obj2Str(Object object){
        try {
            JSON json = (JSON) JSONObject.toJSON(object);
            return json.toString();
        } catch (Exception e){
            logger.info("对象转json字符转换失败，格式错误！", e);
            return null;
        }
    }
}
