package com.ansar.dreamy_checker.business.extractor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class ExcelWorkbookExtractor {

    private static final Pattern PATTERN = Pattern.compile(".*[.]");

    public Workbook extractWorkbook(File excelFile, ExcelWorkbookMode excelWorkbookMode) throws IOException {
        if (excelFile.getName().replaceAll(PATTERN.pattern(), "").equalsIgnoreCase("xls")){
            return excelWorkbookMode == ExcelWorkbookMode.READ ? new HSSFWorkbook(new FileInputStream(excelFile)): new HSSFWorkbook();
        }
        else if (excelFile.getName().replaceAll(PATTERN.pattern(), "").equalsIgnoreCase("xlsx")){
            return excelWorkbookMode == ExcelWorkbookMode.READ ? new XSSFWorkbook(new FileInputStream(excelFile)): new XSSFWorkbook();
        }
        throw new IllegalArgumentException();
    }

}
