package com.ansar.dreamy_checker.business.table;

import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;

public interface TableRow {

    TableCell getCell(String columnName) throws TableColumnNotFoundException;

}
