package com.github.microwww.excel;

import java.io.Serializable;
import java.util.List;

public class ExportConfig implements Serializable {
    SheetConfig sheetConfig;
    List<OperationConfig> operationConfigs;

    public SheetConfig getSheetConfig() {
        return sheetConfig;
    }

    public void setSheetConfig(SheetConfig sheetConfig) {
        this.sheetConfig = sheetConfig;
    }

    public List<OperationConfig> getOperationConfigs() {
        return operationConfigs;
    }

    public void setOperationConfigs(List<OperationConfig> operationConfigs) {
        this.operationConfigs = operationConfigs;
    }
}
