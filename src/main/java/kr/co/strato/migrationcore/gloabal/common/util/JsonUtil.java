package kr.co.strato.migrationcore.gloabal.common.util;


import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T getJsonToConvertObj(String jsonString, Class<T> obj) {
        T convertObj = MAPPER.convertValue(jsonString,obj);
        return convertObj;
    }
}
