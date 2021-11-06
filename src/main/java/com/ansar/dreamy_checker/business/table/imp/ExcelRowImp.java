package com.ansar.dreamy_checker.business.table.imp;

import com.ansar.dreamy_checker.business.table.ExcelCell;
import com.ansar.dreamy_checker.business.table.ExcelRow;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class ExcelRowImp implements ExcelRow {

    private final List<ExcelCell> excelCells;

    public static ExcelRow of(Object[] inputs, Set<String> columnNames) throws IrregularTableException {
        if (inputs.length != columnNames.size())
            throw new IrregularTableException();

        List<String> columns = new ArrayList<>(columnNames);
        List<ExcelCell> excelCells = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++){
            ExcelCell excelCell = new ExcelCelImp(inputs[i], columns.get(columns.size() - i - 1));
            excelCells.add(excelCell);
        }
        return new ExcelRowImp(excelCells);
    }

    @Override
    public ExcelCell getCell(String columnName) throws TableColumnNotFoundException {
        return excelCells.stream().filter(excelCell -> excelCell.getColumnName().equals(columnName))
                .findFirst().orElseThrow(TableColumnNotFoundException::new);
    }
}
