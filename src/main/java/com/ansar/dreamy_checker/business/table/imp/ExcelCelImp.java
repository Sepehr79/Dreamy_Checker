package com.ansar.dreamy_checker.business.table.imp;

import com.ansar.dreamy_checker.business.table.ExcelCell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "value")
public class ExcelCelImp implements ExcelCell {

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
