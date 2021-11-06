package com.ansar.dreamy_checker.business.table.imp;

import com.ansar.dreamy_checker.business.table.TableRow;
import com.ansar.dreamy_checker.business.table.Table;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class SimpleTable implements Table {

    private final String[] columns;

    private final List<TableRow> tableRows = new ArrayList<>();

    public SimpleTable(String ...names) {
        columns = Arrays.stream(names).distinct().toArray(String[]::new);
    }

    @Override
    public void add(Object... inputs) throws IrregularTableException {
        if (inputs.length != columns.length)
            throw new IrregularTableException();

        TableRow excelRow = SimpleTableRow.of(columns, inputs);
        tableRows.add(excelRow);
    }

    @Override
    public List<TableRow> getTableRows() {
        return tableRows;
    }


}
