package com.ansar.dreamy_checker.creator;

import com.ansar.dreamy_checker.business.extractor.ExcelWorkbookExtractor;
import com.ansar.dreamy_checker.business.extractor.ExcelWorkbookMode;
import com.ansar.dreamy_checker.model.pojo.Product;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ProductExcelCreator {

    private static final String KALA_NAME = new String("نام کالا".getBytes(StandardCharsets.UTF_8));
    private static final String KALA_ID = "barcode";
    private static final String KALA_COUNT = "qty1";
    private static final String KALA_TYPE = new String("واحد دوم".getBytes(StandardCharsets.UTF_8));

    private final ExcelWorkbookExtractor excelWorkbookExtractor;

    public void createProductExcel(ObservableList<Product> products, File file) throws IOException {

        Workbook workbook = null;
        try (OutputStream outputStream = new FileOutputStream(file)){
            workbook = excelWorkbookExtractor.extractWorkbook(file, ExcelWorkbookMode.WRITE);
            Sheet sheet = workbook.createSheet("products");

            Row columns = sheet.createRow(0);
            Cell nameCell = columns.createCell(0);
            nameCell.setCellValue(KALA_NAME);
            Cell barcodeCell = columns.createCell(1);
            barcodeCell.setCellValue(KALA_ID);
            Cell countCell = columns.createCell(2);
            countCell.setCellValue(KALA_COUNT);
            Cell typeCell = columns.createCell(3);
            typeCell.setCellValue(KALA_TYPE);

            IntStream.range(1, products.size()).forEach(i -> {
                Row values = sheet.createRow(i);

                Cell name = values.createCell(0);
                name.setCellValue(products.get(i).getName());
                Cell barcode = values.createCell(1);
                barcode.setCellValue(products.get(i).getId());
                Cell count = values.createCell(2);
                count.setCellValue(products.get(i).getCount());
                Cell type = values.createCell(3);
                type.setCellValue(products.get(i).getType());
            });

            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert workbook != null;
            workbook.close();
        }

    }

}
