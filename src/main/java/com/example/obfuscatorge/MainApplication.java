package com.example.obfuscatorge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    // Instance fields
    public static Stage mainStage;

    // Methods
    @Override
    public void start(Stage stage) throws IOException {
        // Creating an FXMLLoader to load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

        // Loading the FXML file into a Parent node
        Parent root = fxmlLoader.load();

        // Creating a Scene with the loaded Parent node and set its dimensions
        Scene scene = new Scene(root, 500, 500);

        // Saving the main stage
        mainStage = stage;

        // Setting properties for the main stage
        stage.setTitle("Obfuscator");
        stage.setMinHeight(500);
        stage.setMinWidth(500);

        // Setting the Scene for the main stage
        stage.setScene(scene);

        // Displaying the main stage
        stage.show();
    }

    /**
     * Entry point, sets property for log4j and launches application (look for {@link #launch})
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        // Getting path of a main class to define a correct path to configuration file
        String classPath = Starter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(classPath);

        // Checking whether the main class executed from a jar
        if (classPath.endsWith(".jar")) {

            // Define jar-suitable path for logj4 configuration file
            System.setProperty("log4j.configurationFile", "com/example/obfuscatorge/log4j2.xml");
        } else {

            // Define project-suitable path for logj4 configuration file
            System.setProperty("log4j.configurationFile", "src/main/resources/com/example/obfuscatorge/log4j2.xml");
        }


        // Launching the JavaFX application
        launch();
    }
}
