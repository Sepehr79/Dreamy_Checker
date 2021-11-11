package com.ansar.dreamy_checker.business.extractor;

import com.ansar.dreamy_checker.business.table.TableCell;
import com.ansar.dreamy_checker.business.table.TableRow;
import com.ansar.dreamy_checker.business.table.excel.ExcelTable;
import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.model.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ExcelProductExtractor {

    public List<Product> extractProducts(ExcelTable excelTable) throws TableColumnNotFoundException {
        List<Product> products = new LinkedList<>();
        List<TableRow> tableRows = excelTable.getTableRows();

        for (TableRow tableRow: tableRows){
            TableCell nameCell = tableRow.getCell("نام کالا");
            TableCell barcodeCell = tableRow.getCell("barcode");
            TableCell secondBarcodeCell = tableRow.getCell("بارکد اصلاح شده");

            Product product = new Product((String) nameCell.getValue(),
                    (String) barcodeCell.getValue(),
                    (String) secondBarcodeCell.getValue());
            products.add(product);
        }

        return products;
    }

}
