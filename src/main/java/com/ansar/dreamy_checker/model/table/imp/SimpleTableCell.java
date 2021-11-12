package com.ansar.dreamy_checker.model.table.imp;

import com.ansar.dreamy_checker.model.table.TableCell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTableCell implements TableCell {

    private Object value;

    private String columnName;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
