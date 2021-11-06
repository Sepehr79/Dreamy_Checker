package com.ansar.dreamy_checker.business.table.imp;

import com.ansar.dreamy_checker.business.table.ExcelRow;
import com.ansar.dreamy_checker.business.table.ExcelTable;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ExcelTableImp implements ExcelTable {

    private final String[] columns;

    private final List<ExcelRow> excelRows = new ArrayList<>();

    public ExcelTableImp(String ...names) {
        columns = Arrays.stream(names).distinct().toArray(String[]::new);
    }

    @Override
    public void add(Object... inputs) throws IrregularTableException {
        if (inputs.length != columns.length)
            throw new IrregularTableException();

        ExcelRow excelRow = ExcelRowImp.of(columns, inputs);
        excelRows.add(excelRow);
    }

    @Override
    public List<ExcelRow> getExcelRows() {
        return excelRows;
    }


}
