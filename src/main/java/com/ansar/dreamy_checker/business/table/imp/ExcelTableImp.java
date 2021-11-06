package com.ansar.dreamy_checker.business.table.imp;

import com.ansar.dreamy_checker.business.table.ExcelRow;
import com.ansar.dreamy_checker.business.table.ExcelTable;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import lombok.Data;

import java.util.*;

@Data
public class ExcelTableImp implements ExcelTable {

    private final Set<String> columns = new HashSet<>();

    private final List<ExcelRow> excelRows = new ArrayList<>();

    public ExcelTableImp(String ...names) {
        columns.addAll(Arrays.asList(names));
    }

    @Override
    public void add(Object... inputs) throws IrregularTableException {
        if (inputs.length != columns.size())
            throw new IrregularTableException();

        ExcelRow excelRow = ExcelRowImp.of(inputs, columns);
        excelRows.add(excelRow);
    }

    @Override
    public List<ExcelRow> getExcelRows() {
        return excelRows;
    }


}
