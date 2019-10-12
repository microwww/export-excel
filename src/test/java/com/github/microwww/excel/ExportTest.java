package com.github.microwww.excel;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ExportTest {

    @Test
    public void readExcel() throws IOException {
        String file = ExportTest.class.getResource("/demo.xlsx").getFile();
        List<ExportConfig> list = Export.parseConfig(file);

        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getSheetConfig().getSheet());
        assertEquals(3, list.get(0).getSheetConfig().getSkipException().size());

        assertEquals(7, list.get(0).getOperationConfigs().size());
        assertNull(list.get(0).getOperationConfigs().get(4).getIndex());
        assertEquals("row.materialConditions", list.get(0).getOperationConfigs().get(4).getOgnl());

        assertEquals(5, list.get(1).getSheetConfig().getSheet());
        assertEquals("row.test", list.get(1).getOperationConfigs().get(1).getOgnl());
    }
}