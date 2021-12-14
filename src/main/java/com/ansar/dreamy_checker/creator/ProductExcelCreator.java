package com.ansar.dreamy_checker.creator;

import com.ansar.dreamy_checker.business.extractor.ExcelWorkbookExtractor;
import com.ansar.dreamy_checker.business.extractor.ExcelWorkbookMode;
import com.ansar.dreamy_checker.model.pojo.Product;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ProductExcelCreator {

    private static final String KALA_NAME = new String("نام کالا".getBytes(StandardCharsets.UTF_8));
    private static final String KALA_ID = "barcode";
    private static final String KALA_COUNT = "qty1";
    private static final String KALA_TYPE = new String("واحد دوم".getBytes(StandardCharsets.UTF_8));

    private final ExcelWorkbookExtractor excelWorkbookExtractor;

    @SneakyThrows
    public void createProductExcel(ObservableList<Product> products, File file) {

        // Throws IllegalFormatFlagsException if the file format was incorrect
        Workbook workbook = excelWorkbookExtractor.extractWorkbook(file, ExcelWorkbookMode.WRITE);

        try (OutputStream outputStream = new FileOutputStream(file)){
            Sheet sheet = workbook.createSheet("products");
            setRowValues(0, sheet, KALA_NAME, KALA_ID, KALA_COUNT, KALA_TYPE);
            IntStream.range(0, products.size()).forEach(i -> setRowValues(
                    i + 1, sheet,
                    products.get(i).getName(),
                    products.get(i).getId(),
                    products.get(i).getCount(),
                    products.get(i).getType()
            ));

            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(workbook).close();
        }
    }

    private void setRowValues(int index, Sheet sheet, String nameCell, String idCell, String countCell, String typeCell){
        Row columns = sheet.createRow(index);
        columns.createCell(0).setCellValue(nameCell);
        columns.createCell(1).setCellValue(idCell);
        columns.createCell(2).setCellValue(countCell);
        columns.createCell(3).setCellValue(typeCell);
    }

}
