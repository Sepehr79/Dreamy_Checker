package com.ansar.dreamy_checker.view;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

@Component
public class DialogViewer {

    public void showDialog(String header, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.setTitle(header);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.getDialogPane().setStyle("-fx-font-family: 'B Yekan';-fx-font-size: 20");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("تایید");

        alert.show();
    }

}
