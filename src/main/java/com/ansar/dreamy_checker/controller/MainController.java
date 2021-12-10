package com.ansar.dreamy_checker.controller;

import com.ansar.dreamy_checker.business.extractor.ExcelProductExtractor;
import com.ansar.dreamy_checker.business.extractor.ExcelWorkbookExtractor;
import com.ansar.dreamy_checker.database.query_executer.FirstIdExtractor;
import com.ansar.dreamy_checker.model.pojo.Product;
import com.ansar.dreamy_checker.model.pojo.UniqueProductProperty;
import com.ansar.dreamy_checker.model.table.excel.ExcelTable;
import com.ansar.dreamy_checker.model.table.exception.IrregularTableException;
import com.ansar.dreamy_checker.model.table.exception.TableColumnNotFoundException;
import com.ansar.dreamy_checker.view.ButtonCell;
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
import javafx.scene.control.cell.TextFieldTableCell;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MainController implements Initializable {



    private @FXML TableView<Product> kalaTable;
    private @FXML TableColumn<Product, String> kalaNameColumn;
    private @FXML TableColumn<Product, String> kalaIdColumn;
    private @FXML TableColumn<Product, String> type;
    private @FXML TableColumn<Product, String> count;
    private @FXML TableColumn<Product, Boolean> isSelected;
    private @FXML TableColumn<Product, Boolean> delete;

    private @FXML TextField kalaCodeTextField;

    private final FirstIdExtractor firstIdExtractor;

    private final ExcelProductExtractor excelProductExtractor;

    private final ExcelWorkbookExtractor excelWorkbookExtractor;

    private final DialogViewer dialogViewer;

    private static final ObservableList<UniqueProductProperty> PRODUCT_ID_REPOSITORY = FXCollections.observableArrayList();

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

        delete.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue() != null));
        delete.setCellFactory(p -> new ButtonCell<>(kalaTable));
        delete.setSortable(false);

        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        count.setCellFactory(TextFieldTableCell.forTableColumn());
        count.setOnEditCommit(event -> {
            event.getRowValue().setCount(event.getNewValue());
            event.getTableView().refresh();
        });

        isSelected.setCellFactory(param -> new CheckBoxTableCell<>());
        isSelected.setCellValueFactory(param -> new SimpleBooleanProperty(
                PRODUCT_ID_REPOSITORY.contains(
                        new UniqueProductProperty(param.getValue().getId())
                ))
        );
    }

    private void inputTextFieldConfiguration(){
        kalaCodeTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !kalaCodeTextField.getText().equals("")){
                String id = kalaCodeTextField.getText().trim();
                String queryId;
                if (tableContainsId(id)){
                    log.info("Kala found on table: {}", id);
                    PRODUCT_ID_REPOSITORY.add(new UniqueProductProperty(id));
                } else if ((queryId = firstIdExtractor.extractFirstId(id)) != null){
                    log.info("Kala found on database: {}", queryId);
                    PRODUCT_ID_REPOSITORY.add(new UniqueProductProperty(queryId));
                } else {
                    dialogViewer.showDialog("خطا", "کالا یافت نشد", Alert.AlertType.ERROR);
                }
                kalaTable.refresh();
                kalaCodeTextField.clear();
            }
        });
    }

    private boolean tableContainsId(String id){
        return kalaTable.getItems().stream().map(Product::getId).collect(Collectors.toList()).contains(id);
    }
}
