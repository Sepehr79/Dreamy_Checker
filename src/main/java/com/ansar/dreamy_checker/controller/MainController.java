package com.ansar.dreamy_checker.controller;

import com.ansar.dreamy_checker.business.extractor.ExcelProductExtractor;
import com.ansar.dreamy_checker.business.table.excel.ExcelTable;
import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.model.pojo.Product;
import com.ansar.dreamy_checker.model.pojo.UniqueProductProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@Slf4j
public class MainController implements Initializable {

    private @FXML TableView<Product> kalaTable;
    private @FXML TableColumn<Product, String> kalaSecondIdColumn;
    private @FXML TableColumn<Product, String> kalaNameColumn;
    private @FXML TextField kalaCodeTextField;
    private @FXML TableColumn<Product, String> kalaIdColumn;
    private @FXML TableColumn<Product, Boolean> isSelected;

    private final ExcelProductExtractor excelProductExtractor;

    private static final ObservableList<UniqueProductProperty> INPUT_PRODUCTS = FXCollections.observableArrayList();

    private static final FileChooser FILE_CHOOSER = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Page configurations
        tableConfiguration();
        inputTextFieldConfiguration();
    }

    public void selectFile() throws IOException, IrregularTableException, TableColumnNotFoundException {

        File file = FILE_CHOOSER.showOpenDialog(new Stage());
        if (file == null){
            log.info("Exiting FILE_CHOOSER");
            return;
        }

        try(FileInputStream inputStream = new FileInputStream(file)) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            ExcelTable excelTable = new ExcelTable(xssfSheet.iterator());

            kalaTable.getItems().addAll(excelProductExtractor.extractProducts(excelTable));
        }
    }

    private void tableConfiguration(){
        // Columns
        kalaNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kalaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        kalaSecondIdColumn.setCellValueFactory(new PropertyValueFactory<>("secondId"));

        isSelected.setCellFactory(param -> new CheckBoxTableCell<>());
        isSelected.setCellValueFactory(param -> new SimpleBooleanProperty(INPUT_PRODUCTS.contains(
                new UniqueProductProperty(param.getValue().getId(), "")))
        );
    }

    private void inputTextFieldConfiguration(){
        kalaCodeTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !kalaCodeTextField.getText().equals("")){
                INPUT_PRODUCTS.add(new UniqueProductProperty(kalaCodeTextField.getText(), ""));
                kalaTable.refresh();
                kalaCodeTextField.clear();
            }
        });
    }
}
