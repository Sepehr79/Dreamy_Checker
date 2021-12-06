package com.ansar.dreamy_checker.view;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class ButtonCell<T> extends TableCell<T, Boolean> {

    private final Button button = new Button("×");

    public ButtonCell(TableView<T> tableView){
        button.setOnAction(event -> {
            int selectedIndex = getTableRow().getIndex();
            tableView.getItems().remove(selectedIndex);
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){
            setGraphic(button);
        }else
            setGraphic(null);
    }
}
