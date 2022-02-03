package com.ansar.dreamy_checker;

import com.ansar.dreamy_checker.view.ViewLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ComponentScan("com.ansar.dreamy_checker")
public class Main extends Application {

    private ViewLoader viewLoader;

    private AnnotationConfigApplicationContext applicationContext;

    @Override
    public void init(){
        applicationContext = new AnnotationConfigApplicationContext(Main.class);
        viewLoader = applicationContext.getBean("viewLoader", ViewLoader.class);
    }

    @Override
    public void stop(){
        applicationContext.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(viewLoader.page(ViewLoader.MAIN_PAGE)));
        primaryStage.show();
    }
}
