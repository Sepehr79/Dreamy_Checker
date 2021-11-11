package com.ansar.dreamy_checker.business.table.excel;

import com.ansar.dreamy_checker.business.table.TableCell;
import com.ansar.dreamy_checker.business.table.TableRow;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.imp.SimpleTable;
import com.ansar.dreamy_checker.business.table.imp.SimpleTableCell;
import com.ansar.dreamy_checker.business.table.imp.SimpleTableRow;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class ExcelTable extends SimpleTable {

    private final int startingColumn;
    private final int startingRow;

    private static final DataFormatter DATE_FORMAT = new DataFormatter();
    
    /**
     * Creating a full table from excel row iterator
     */
    public ExcelTable(XSSFSheet xssfSheet) throws IrregularTableException {
        Row firstRow = xssfSheet.getRow(xssfSheet.getFirstRowNum());
        this.startingRow = xssfSheet.getFirstRowNum();
        this.startingColumn = firstRow.getFirstCellNum();

        setColumns(getCellList(firstRow).stream().distinct().toArray(String[]::new));

        for (int i = startingRow + 1; i < xssfSheet.getLastRowNum(); i++){
            List<String> values = getCellList(xssfSheet.getRow(i));
            add(values.toArray());
        }
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(getColumns())
                .sequential().forEach(s -> stringBuilder.append(s).append("\t\t\t"));
        stringBuilder.append("\n");

        for (TableRow tableRow : getTableRows()) {
            SimpleTableRow row = (SimpleTableRow) tableRow;
            for (TableCell cell : row.getExcelCells()) {
                SimpleTableCell tableCell = (SimpleTableCell) cell;
                stringBuilder.append(tableCell.getValue()).append("\t\t\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private List<String> getCellList(Row row){
        List<String> columnsList = new LinkedList<>();
        for (int i = startingColumn; i < row.getLastCellNum(); i++){
            Cell cell = row.getCell(i);
            String value = DATE_FORMAT.formatCellValue(cell);
            log.info("Cell: {}", value);
            columnsList.add(value);
        }
        return columnsList;
    }
}
