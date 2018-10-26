package org.idey.algo.threadorder;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, ? super Object> map = new HashMap<>();

    public void setParam(String key, Object value){
        map.put(key,value);
    }

    public Object get(String key){
        return map.get(key);
    }
}
