package com.ansar.dreamy_checker.business.table;

import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.business.table.imp.SimpleTable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelTableTest {

    @Test
    void testExcelTable() throws IrregularTableException, TableColumnNotFoundException {

        Table excelTable = new SimpleTable("name", "code1", "code2");
        excelTable.add("sepehr", "123", "456");
        excelTable.add("soroosh", "789", "012");

        List<TableRow> rows = excelTable.getTableRows();

        assertEquals("sepehr" , rows.get(0).getCell("name").getValue());
        assertEquals("789", rows.get(1).getCell("code1").getValue());
    }

}
