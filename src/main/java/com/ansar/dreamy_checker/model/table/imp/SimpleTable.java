package com.ansar.dreamy_checker.model.table.imp;

import com.ansar.dreamy_checker.model.table.Table;
import com.ansar.dreamy_checker.model.table.TableCell;
import com.ansar.dreamy_checker.model.table.TableRow;
import com.ansar.dreamy_checker.model.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.model.table.exception.TableIndexOutOfBoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class SimpleTable implements Table {

    private String[] columns;

    private final List<TableRow> tableRows = new ArrayList<>();

    public SimpleTable(String ...columns) {
        this.columns = Arrays.stream(columns).distinct().toArray(String[]::new);
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

    public void setColumns(String ...columns){
        this.columns = Arrays.stream(columns).distinct().toArray(String[]::new);
    }

}
