package com.ansar.dreamy_checker.controller;

import com.ansar.dreamy_checker.business.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.business.table.imp.SimpleTable;
import com.ansar.dreamy_checker.model.pojo.Product;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    private @FXML TextField kalaCodeTextField;

    private @FXML TableView<Product> kalaTable;
    private @FXML TableColumn<Product, String> kalaAnbarColumn;
    private @FXML TableColumn<Product, String> kalaPriceColumn;
    private @FXML TableColumn<Product, String> kalaNameColumn;
    private @FXML TableColumn<Product, String> kalaIdColumn;
    private @FXML TableColumn<Product, Boolean> isSelected;

    private static final ObservableList<Product> INPUT_PRODUCTS = FXCollections.observableArrayList();
    private static final FileChooser FILE_CHOOSER = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Page configurations
        tableConfiguration();
        inputTextFieldConfiguration();

        testConfiguration();
    }

    public void selectFile(ActionEvent actionEvent) throws IOException, IrregularTableException {

        FileChooser fileChooser = new FileChooser();
        try(FileInputStream inputStream = new FileInputStream(fileChooser.showOpenDialog(new Stage()))) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            SimpleTable simpleTable = new SimpleTable(xssfSheet.rowIterator());
        }
    }

    private void tableConfiguration(){
        // Columns
        kalaAnbarColumn.setCellValueFactory(new PropertyValueFactory<>("anbarId"));
        kalaPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        kalaNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kalaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        isSelected.setCellFactory(param -> new CheckBoxTableCell<>());
        isSelected.setCellValueFactory(param -> new SimpleBooleanProperty(INPUT_PRODUCTS.contains(param.getValue())));
    }

    private void inputTextFieldConfiguration(){
        kalaCodeTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !kalaCodeTextField.getText().equals("")){
                Product product = new Product(kalaCodeTextField.getText());
                INPUT_PRODUCTS.add(product);
                kalaTable.refresh();
                kalaCodeTextField.clear();
            }
        });
    }

    private void testConfiguration(){
        kalaTable.getItems().addAll(
                new Product("1", "dwq", "456", "110"),
                new Product("2", "dwqdsa", "41153", "120"),
                new Product("3", "fweijhfew", "14564156", "130"),
                new Product("4", "fewfew", "1541", "140")
        );
    }
}
