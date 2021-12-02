package com.ansar.dreamy_checker.business.extractor;

import com.ansar.dreamy_checker.model.pojo.Product;
import com.ansar.dreamy_checker.model.table.TableCell;
import com.ansar.dreamy_checker.model.table.TableRow;
import com.ansar.dreamy_checker.model.table.excel.ExcelTable;
import com.ansar.dreamy_checker.model.table.exception.TableColumnNotFoundException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@Component
public class ExcelProductExtractor {

    private static final String KALA_NAME = new String("نام کالا".getBytes(StandardCharsets.UTF_8));
    private static final String KALA_ID = new String("کد کالا".getBytes(StandardCharsets.UTF_8));
    private static final String KALA_COUNT = new String("شارژ واحد دوم".getBytes(StandardCharsets.UTF_8));
    private static final String KALA_TYPE = new String("واحد دوم".getBytes(StandardCharsets.UTF_8));

    public List<Product> extractProducts(ExcelTable excelTable) throws TableColumnNotFoundException {
        List<Product> products = new LinkedList<>();

        for (TableRow tableRow: excelTable.getTableRows()){
            TableCell nameCell = tableRow.getCell(KALA_NAME);
            TableCell barcodeCell = tableRow.getCell(KALA_ID);
            TableCell countCell = tableRow.getCell(KALA_COUNT);
            TableCell typeCell = tableRow.getCell(KALA_TYPE);

            products.add(
                    Product.builder()
                            .id((String) barcodeCell.getValue())
                            .name((String) nameCell.getValue())
                            .count((String) countCell.getValue())
                            .type((String) typeCell.getValue())
                        .build()
            );
        }

        return products;
    }

}
