package com.ansar.dreamy_checker.model.table;

import com.ansar.dreamy_checker.model.table.excel.ExcelTable;
import com.ansar.dreamy_checker.model.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.model.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.model.table.exception.TableIndexOutOfBoundException;
import com.ansar.dreamy_checker.model.table.imp.SimpleTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
class ExcelTableTest {

    private static final String EXCEL_FILE = "testing_excel.xlsx";

    @Test
    void testExcelTable() throws IrregularTableException, TableColumnNotFoundException {

        Table excelTable = new SimpleTable("name", "code1", "code2");
        excelTable.add("sepehr", "123", "456");
        excelTable.add("soroosh", "789", "012");

        List<TableRow> rows = excelTable.getTableRows();

        assertEquals("sepehr" , rows.get(0).getCell("name").getValue());
        assertEquals("789", rows.get(1).getCell("code1").getValue());
    }

    @Test
    void testReadingExcelFile() throws IOException {
        try(InputStream inputStream =
                    this.getClass().getClassLoader().getResourceAsStream(EXCEL_FILE)) {
            assertNotNull(inputStream);

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            try {
                ExcelTable simpleTable = new ExcelTable(xssfSheet);
                log.info("\n" + simpleTable);
                assertEquals( "456412" ,simpleTable.getCell(0, 0).getValue());
                assertEquals("ماست", simpleTable.getCell(2, 2).getValue());
                simpleTable.getCell(50, 100);
                fail();
            } catch (IrregularTableException e) {
                e.printStackTrace();
                fail();
            } catch (TableIndexOutOfBoundException e){
                log.info(e.toString());
            }
        }
    }
}
