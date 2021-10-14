package com.ansar.dreamy_checker.controller;

import com.ansar.dreamy_checker.model.pojo.Product;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    public TextField kalaCodeTextField;

    public TableView<Product> kalaTable;
    public TableColumn<Product, String> kalaAnbarColumn;
    public TableColumn<Product, String> kalaPriceColumn;
    public TableColumn<Product, String> kalaNameColumn;
    public TableColumn<Product, String> kalaIdColumn;
    public TableColumn<Product, Boolean> isSelected;

    private static final ObservableList<Product> INPUT_PRODUCTS = FXCollections.observableArrayList();
    private static final FileChooser FILE_CHOOSER = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Page configurations
        tableConfiguration();
        inputTextFieldConfiguration();

        Product product = new Product("1", "سلام", "65132", "13532");
        kalaTable.getItems().add(product);

    }

    public void selectFile(ActionEvent actionEvent) throws IOException, SQLException {
//        File excelFile = FILE_CHOOSER.showOpenDialog(new Stage());
//        FileInputStream excelInputStream = new FileInputStream(excelFile);
//
//        Workbook workbook = null;
//
//        if (excelFile.getName().endsWith("xls")){
//            workbook = new HSSFWorkbook(excelInputStream);
//        }else if (excelFile.getName().endsWith("xlsx")){
//            workbook = new XSSFWorkbook(excelInputStream);
//        }
//        assert workbook != null;
//        Sheet firstSheet = workbook.getSheetAt(0);
//        Iterator<Row> iterator = firstSheet.iterator();
//        while (iterator.hasNext()){
//            Row row = iterator.next();
//
//            Iterator<Cell> cellIterator = row.cellIterator();
//            while (cellIterator.hasNext()){
//                Cell cell = cellIterator.next();
//                switch (cell.getCellType()){
//                    case NUMERIC:
//                        System.out.println(cell.getNumericCellValue());
//                        break;
//                    case STRING:
//                        System.out.println(cell.getStringCellValue());
//                        break;
//                    case BOOLEAN:
//                        System.out.println(cell.getBooleanCellValue());
//                        break;
//                }
//            }
//        }

        Product product = new Product();
        product.setId("1");
        INPUT_PRODUCTS.add(product);
        kalaTable.refresh();
    }

    private void tableConfiguration(){
        // Columns
        kalaAnbarColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("anbarId"));
        kalaPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        kalaNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        kalaIdColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));

        isSelected.setCellFactory(param -> new CheckBoxTableCell<>());
        isSelected.setCellValueFactory(param -> new SimpleBooleanProperty(INPUT_PRODUCTS.contains(param.getValue())));
    }

    private void inputTextFieldConfiguration(){
        kalaCodeTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                // Todo
                kalaCodeTextField.clear();
            }
        });
    }
}
