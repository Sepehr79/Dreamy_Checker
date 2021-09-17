package com.ansar.dreamy_checker.view;

import com.ansar.dreamy_checker.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ViewLoader {

    public static final String MAIN_PAGE = "pages/main.fxml";

    private static final AnnotationConfigApplicationContext springContext =
            new AnnotationConfigApplicationContext(Main.class);

    public Parent page(String page) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
        fxmlLoader.setLocation(ViewLoader.class.getResource(page));

        return fxmlLoader.load();
    }

}
