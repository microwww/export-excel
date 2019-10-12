package com.github.microwww.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SheetConfig implements Serializable {

    Map<String, List<String>> map;

    public SheetConfig(Map<String, List<String>> map) {
        this.map = map;
    }

    public SheetConfig(List<List<String>> lists) {
        this.map = list2map(lists);
    }

    public String getConfigName() {
        List<String> list = map.get("configName");
        if (list.isEmpty()) {
            return null;
        }
        return getFirst("configName", "config");
    }

    public int getSkip() {
        return Double.valueOf(getFirst("skip", "1")).intValue();
    }

    public int getSheet() {
        return Double.valueOf(getFirst("sheet", "0")).intValue();
    }

    public String getRowName() {
        return getFirst("rowName", "row");
    }

    public String getListName() {
        return getFirst("listName", "list");
    }

    /**
     * @return not null
     */
    public List<String> getSkipException() {
        return new ArrayList<>(map.get("skipException"));
    }

    public String getFirst(String key, String def) {
        List<String> list = map.get(key);
        if (list.isEmpty()) {
            return def;
        }
        return list.iterator().next();
    }

    public static Map<String, List<String>> list2map(List<List<String>> list) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (List<String> ls : list) {
            if (ls == null || ls.isEmpty()) {
                continue;
            }
            String key = ls.get(0);
            List<String> target = new ArrayList<>();
            map.put(key, target);
            for (int i = 1; i < ls.size(); i++) {
                target.add(ls.get(i));
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "::" + this.map;
    }
}
