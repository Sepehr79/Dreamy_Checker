package com.ansar.dreamy_checker.business.extractor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class ExcelWorkbookExtractor {

    public Workbook extractWorkbook(FileInputStream fileInputStream, File excelFile) throws IOException {
        if (excelFile.getName().replaceAll(".*[.]", "").equalsIgnoreCase("xls"))
            return new HSSFWorkbook(fileInputStream);
        else if (excelFile.getName().replaceAll(".*[.]", "").equalsIgnoreCase("xlsx"))
            return new XSSFWorkbook(fileInputStream);
        throw new IllegalArgumentException();
    }

}
