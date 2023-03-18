package homework3.transliterator.dated_map;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap{
    Map<String, String> mainMap;
    Map<String, Date> dateMap;
    public DatedMapImpl() {
        mainMap = new HashMap<>();
        dateMap = new HashMap<>();
    }
    @Override
    public void put(String key, String value) {
        mainMap.put(key, value);
        dateMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return mainMap.getOrDefault(key, null);
    }

    @Override
    public boolean containsKey(String key) {
        return mainMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        mainMap.remove(key);
        dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return mainMap.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return dateMap.getOrDefault(key, null);
    }
}
