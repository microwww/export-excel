package com.github.microwww.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OperationConfig implements Serializable {
    private String ognl;
    private Integer index;
    private String format;
    private String other;

    public OperationConfig() {
    }

    public static List<OperationConfig> parse2list(List<List<String>> list) {
        List<OperationConfig> cfs = new ArrayList<>();
        for (List<String> ls : list) {
            cfs.add(parse(ls));
        }
        return cfs;
    }

    public static OperationConfig parse(List<String> list) {
        OperationConfig cf = new OperationConfig();
        cf.ognl = list.get(0);
        try {
            try {
                cf.index = Double.valueOf(list.get(1)).intValue();
            } catch (NullPointerException | NumberFormatException e) {// ignore
            }
            cf.format = list.get(2);
            cf.other = list.get(3);
        } catch (IndexOutOfBoundsException e) {// ignore
        }
        return cf;
    }

    public String getOgnl() {
        return ognl;
    }

    public void setOgnl(String ognl) {
        this.ognl = ognl;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "OperationConfig{" +
                "ognl='" + ognl + '\'' +
                ", index=" + index +
                ", format='" + format + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
