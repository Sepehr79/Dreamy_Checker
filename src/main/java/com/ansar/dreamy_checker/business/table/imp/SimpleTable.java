package com.ansar.dreamy_checker.business.table.imp;

import com.ansar.dreamy_checker.business.table.Table;
import com.ansar.dreamy_checker.business.table.TableCell;
import com.ansar.dreamy_checker.business.table.TableRow;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.exception.TableIndexOutOfBoundException;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;

@Data
public class SimpleTable implements Table {

    private final String[] columns;

    private final List<TableRow> tableRows = new ArrayList<>();

    public SimpleTable(String ...names) {
        columns = Arrays.stream(names).distinct().toArray(String[]::new);
    }

    /**
     * Creating a full table from excel row iterator
     */
    public SimpleTable(Iterator<Row> rowIterator) throws IrregularTableException {
        Row firstRow = rowIterator.next();
        Iterator<Cell> cellIterator = firstRow.cellIterator();
        this.columns = getCellList(cellIterator).stream().distinct().toArray(String[]::new);

        while (rowIterator.hasNext()){
            List<String> values = getCellList(rowIterator.next().cellIterator());
            add(values.toArray());
        }
    }

    @Override
    public void add(Object ...inputs) throws IrregularTableException {
        if (inputs.length != columns.length)
            throw new IrregularTableException();

        TableRow excelRow = SimpleTableRow.of(columns, inputs);
        tableRows.add(excelRow);
    }

    @Override
    public List<TableRow> getTableRows() {
        return tableRows;
    }

    @Override
    public TableCell getCell(int column, int row) throws TableIndexOutOfBoundException {
        if (row >= tableRows.size())
            throw new TableIndexOutOfBoundException();
        TableRow tableRow = this.tableRows.get(row);
        return tableRow.getCell(column);
    }

    private List<String> getCellList(Iterator<Cell> cellIterator){
        List<String> columnsList = new LinkedList<>();
        while (cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            switch (cell.getCellType()){
                case STRING:
                    columnsList.add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    columnsList.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                default:
                    columnsList.add("");
            }
        }
        return columnsList;
    }

}
