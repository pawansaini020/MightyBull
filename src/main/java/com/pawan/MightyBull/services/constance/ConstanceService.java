package com.pawan.MightyBull.services.constance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ConstanceService {


    private final ConstanceManager constanceManager;

    @Autowired
    private ConstanceService(ConstanceManager constanceManager) {
        this.constanceManager = constanceManager;
    }

    public Object getConstanceValue(String key) {

        Map<String, Object> constanceValue = constanceManager.getConstanceMap(false).get(key);

        if (constanceValue == null || constanceValue.get(key)!=null) {
            log.error(key + " not found in constances");
        }
        return constanceValue.get(key);
    }

    public JsonObject getHash(String key) {
        Object constanceValue = null;
        try {
            constanceValue = getConstanceValue(key);
            return GsonUtils.getGson().toJsonTree(constanceValue).getAsJsonObject();
        } catch (Exception e) {
            log.error("Error while parsing value: {} as json", constanceValue, e);
            return null;
        }
    }

    public Boolean getAsBoolean(String key, boolean defaultValue) {
        Object constanceValue = null;
        try {
            constanceValue = getConstanceValue(key);
            return GsonUtils.getGson().toJsonTree(constanceValue).getAsBoolean();
        } catch (Exception e) {
            log.error("Error while parsing value: {} as boolean", constanceValue);
            return defaultValue;
        }
    }

    public String getAsString(String key) {
        Object constanceValue = null;
        try {
            constanceValue = getConstanceValue(key);
            return GsonUtils.getGson().fromJson((String) constanceValue, String.class);
        } catch (Exception e) {
            log.error("Error while parsing value: {} as string", constanceValue);
        }
        return null;
    }

    public List<String> getAsList(String key) {
        Object constanceValue = null;
        try {
            constanceValue = getConstanceValue(key);
            TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {};
            return GsonUtils.getGson().fromJson((String) constanceValue, typeToken.getType());
        } catch (Exception e) {
            log.error("Error while parsing value: {} as string", constanceValue);
        }
        return new ArrayList<>();
    }

    public JsonArray getAsJsonArray(String key) {
        Object constanceValue = null;
        try {
            constanceValue = getConstanceValue(key);
            return GsonUtils.getGson().toJsonTree(constanceValue).getAsJsonArray();
        } catch (Exception e) {
            log.error("Error while parsing value: {} as JsonArray", constanceValue);
        }
        return new JsonArray();
    }


    public Map<String, Object> getAsMap(String key) {
        try {
            Map<String, Object> constanceValue = constanceManager.getConstanceMap(false).get(key);;
            return constanceValue;
        } catch (Exception e) {
            log.error("Error while parsing key: {} as Map<String, Object>", key);
        }
        return new HashMap<>();
    }


//    public Map<String, Map<String, Object>> getAsSingleNestedMap(String key) {
//        Object constanceValue = null;
//        try {
//            constanceValue = getConstanceValue(key);
//            return JsonUtil.cvtObjToSingleNestedMap(constanceValue);
//        } catch (Exception e) {
//            log.error("Error while parsing value: {} as Map<String,Map<String,Object>>", constanceValue);
//        }
//        return new HashMap<>();
//    }

    public void reloadConstance() {
        Map<String, Map<String, Object>> constanceValue = constanceManager.getConstanceMap(true);
    }

    public void updateConstanceValue(String key, Map<String, Object> value) {
        constanceManager.updateConstanceValue(key, value);
    }
}
