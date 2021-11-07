package com.ansar.dreamy_checker.business.table;

import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.business.table.exception.TableIndexOutOfBoundException;

public interface TableRow {

    TableCell getCell(String columnName) throws TableColumnNotFoundException;

    TableCell getCell(int column) throws TableIndexOutOfBoundException;

}
