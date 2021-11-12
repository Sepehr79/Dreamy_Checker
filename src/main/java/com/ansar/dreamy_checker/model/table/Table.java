package com.ansar.dreamy_checker.model.table;

import com.ansar.dreamy_checker.model.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.model.table.exception.TableIndexOutOfBoundException;

import java.util.List;

public interface Table {

    void add(Object ...inputs) throws IrregularTableException;

    List<TableRow> getTableRows();

    TableCell getCell(int column, int row) throws TableIndexOutOfBoundException;

}
