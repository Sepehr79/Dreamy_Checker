package com.ansar.dreamy_checker.business.table;

import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;

public interface ExcelRow {

    ExcelCell getCell(String columnName) throws TableColumnNotFoundException;

}
