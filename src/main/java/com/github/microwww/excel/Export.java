package com.github.microwww.excel;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Export {

    private static final Logger logger = LoggerFactory.getLogger(Export.class);
    public static final String CONFIG_SHEET_NAME = "_config";
    public static final String INI_CONFIG = "CONFIG";
    public static final String INI_PROPERTIES = "PROPERTIES";

    public static List<ExportConfig> parseConfig(String file) throws IOException {
        try (InputStream inp = new FileInputStream(file)) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheet(CONFIG_SHEET_NAME);
            if (sheet == null) {
                throw new ExportException("Not find sheel for name :" + CONFIG_SHEET_NAME);
            }

            ArrayListValuedHashMap<String, List<List<String>>> configs = readConfig(sheet);
            List<List<List<String>>> config = configs.get(INI_CONFIG);
            List<List<List<String>>> properties = configs.get(INI_PROPERTIES);

            List<ExportConfig> ec = new ArrayList<>();
            for (int i = 0; i < config.size(); i++) {
                ExportConfig cf = new ExportConfig();
                cf.setSheetConfig(new SheetConfig(config.get(i)));
                if (properties.size() > i) {
                    cf.setOperationConfigs(OperationConfig.parse2list(properties.get(i)));
                } else { // 以最少数量为准
                    break;
                }
                ec.add(cf);
                logger.info("read template excel config:{}, list:{}", cf.getSheetConfig(), cf.getOperationConfigs());
            }


            return ec;
        }
    }

    public static ArrayListValuedHashMap<String, List<List<String>>> readConfig(Sheet sheet) {
        ArrayListValuedHashMap<String, List<List<String>>> configs = new ArrayListValuedHashMap<>();
        List<List<String>> current = new ArrayList<>(); // 默认的会被忽略掉
        Iterator<Row> rows = sheet.rowIterator();
        for (int r = 0; rows.hasNext(); r++) {
            Row row = rows.next();
            // cell 0
            Cell cell = row.getCell(0);
            String val = getCellString(cell);

            if (!isBlank(val)) {
                current = new ArrayList<>();
                configs.put(val, current);
                continue;
            }

            // cell 1
            Cell next = row.getCell(1);
            String key = getCellString(next);
            if (isBlank(key)) {
                logger.warn("skip [{},{}]", r + 1, 1 + 1);
                break;
            }
            List<String> list = new ArrayList<>();
            list.add(key);
            // cell from 2
            for (int i = 2; i < row.getLastCellNum(); i++) {
                next = row.getCell(i);
                val = getCellString(next);
                list.add(val);
            }
            current.add(list);
        }
        return configs;
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        return str.trim().length() == 0;
    }

    public static String getCellString(Cell cell) {
        //Cell cell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        if (cell == null) {
            return null;
        }
        CellType type = cell.getCellType();
        switch (type) {
            case BOOLEAN: {
                return "" + cell.getBooleanCellValue();
            }
            case NUMERIC: {
                return "" + cell.getNumericCellValue();
            }
            case STRING: {
                return cell.getStringCellValue();
            }
        }
        return null;
    }
}
