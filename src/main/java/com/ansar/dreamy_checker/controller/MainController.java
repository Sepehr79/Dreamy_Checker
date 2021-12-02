package com.ansar.dreamy_checker.controller;

import com.ansar.dreamy_checker.business.extractor.ExcelProductExtractor;
import com.ansar.dreamy_checker.business.extractor.ExcelWorkbookExtractor;
import com.ansar.dreamy_checker.model.pojo.Product;
import com.ansar.dreamy_checker.model.pojo.UniqueProductProperty;
import com.ansar.dreamy_checker.model.table.excel.ExcelTable;
import com.ansar.dreamy_checker.model.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.model.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.view.DialogViewer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    private @FXML TableColumn<Product, Integer> count;
    private @FXML TableColumn<Product, String> kalaNameColumn;
    private @FXML TableColumn<Product, String> kalaIdColumn;
    private @FXML TableColumn<Product, Boolean> isSelected;
    private @FXML TableColumn<Product, String> type;
    private @FXML TextField kalaCodeTextField;

    private final ExcelProductExtractor excelProductExtractor;

    private final ExcelWorkbookExtractor excelWorkbookExtractor;

    private final DialogViewer dialogViewer;

    private static final ObservableList<UniqueProductProperty> INPUT_PRODUCTS = FXCollections.observableArrayList();

    private static final FileChooser FILE_CHOOSER = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Page configurations
        tableConfiguration();
        inputTextFieldConfiguration();
    }

    public void selectFile() {

        File file = FILE_CHOOSER.showOpenDialog(new Stage());
        if (file == null){
            log.info("Exiting FILE_CHOOSER");
            return;
        }

        try(FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = excelWorkbookExtractor.extractWorkbook(inputStream, file);
            Sheet sheet = workbook.getSheetAt(0);
            ExcelTable excelTable = new ExcelTable(sheet);
            kalaTable.getItems().addAll(excelProductExtractor.extractProducts(excelTable));
        }catch (TableColumnNotFoundException tableColumnNotFoundException) {
            dialogViewer.showDialog("خطا", "نام ستون ها قابل شناسایی نیست", Alert.AlertType.ERROR);
            tableColumnNotFoundException.printStackTrace();
        }catch (IrregularTableException irregularTableException){
            dialogViewer.showDialog("خطا", "امکان خواندن جدول وجود ندارد", Alert.AlertType.ERROR);
            irregularTableException.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void tableConfiguration(){
        // Columns
        kalaNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kalaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));

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
