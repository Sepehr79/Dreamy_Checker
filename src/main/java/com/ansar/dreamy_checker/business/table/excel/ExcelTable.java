package com.ansar.dreamy_checker.business.table.excel;

import com.ansar.dreamy_checker.business.table.TableCell;
import com.ansar.dreamy_checker.business.table.TableRow;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.imp.SimpleTable;
import com.ansar.dreamy_checker.business.table.imp.SimpleTableCell;
import com.ansar.dreamy_checker.business.table.imp.SimpleTableRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExcelTable extends SimpleTable {

    private static final DataFormatter DATE_FORMAT = new DataFormatter();
    
    /**
     * Creating a full table from excel row iterator
     */
    public ExcelTable(Iterator<Row> rowIterator) throws IrregularTableException {
        super(getCellList(rowIterator).stream().distinct().toArray(String[]::new));

        while (rowIterator.hasNext()){
            List<String> values = getCellList(rowIterator);
            add(values.toArray());
        }
    }

    private static List<String> getCellList(Iterator<Row> rowIterator){
        Row firstRow = rowIterator.next();
        Iterator<Cell> cellIterator = firstRow.cellIterator();
        List<String> columnsList = new LinkedList<>();
        while (cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String value = DATE_FORMAT.formatCellValue(cell);
            columnsList.add(value);
        }
        return columnsList;
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
}
