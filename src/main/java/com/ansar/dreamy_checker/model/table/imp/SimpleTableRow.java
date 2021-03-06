package com.ansar.dreamy_checker.model.table.imp;

import com.ansar.dreamy_checker.model.table.TableCell;
import com.ansar.dreamy_checker.model.table.TableRow;
import com.ansar.dreamy_checker.model.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.model.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.model.table.exception.TableIndexOutOfBoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SimpleTableRow implements TableRow {

    private final List<TableCell> excelCells;

    public static TableRow of(String[] columnNames , Object[] inputs) throws IrregularTableException {
        if (inputs.length != columnNames.length)
            throw new IrregularTableException();

        List<TableCell> excelCells = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++){
            TableCell tableCell = new SimpleTableCell(inputs[i], columnNames[i]);
            excelCells.add(tableCell);
        }
        return new SimpleTableRow(excelCells);
    }

    @Override
    public TableCell getCell(String columnName) throws TableColumnNotFoundException {
        return excelCells.stream().filter(excelCell -> excelCell.getColumnName().equals(columnName))
                .findFirst().orElseThrow(TableColumnNotFoundException::new);
    }

    @Override
    public TableCell getCell(int column) throws TableIndexOutOfBoundException {
        if (column >= excelCells.size())
            throw new TableIndexOutOfBoundException();
        return this.excelCells.get(column);
    }
}
