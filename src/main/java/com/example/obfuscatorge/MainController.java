package com.example.obfuscatorge;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MainController {

    // Reference fields
    private static final Logger logger = LogManager.getLogger(MainController.class);

    // Instance fields
    private List<File> selectedFiles = new ArrayList<File>();
    @FXML
    public Button fileSelectionButton;
    @FXML
    public Button obfuscationButton;

    // Methods

    /**
     * Closes the application
     *
     * @param actionEvent The action event representing a button click
     */
    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Shows alert window with specified parameters
     *
     * @param title     Title of window
     * @param message   Message to be shown
     * @param alertType Type of window
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Implements obfuscation of list of files and creates theirs obfuscated copies.
     * Invokes {@link Obfuscator#obfuscate} and {@link #createFile}
     *
     * @param actionEvent The action event representing a button click
     */
    @FXML
    private void obfuscateFiles(ActionEvent actionEvent) {

        for (File file : selectedFiles) {

            Obfuscator obfuscator = new Obfuscator();

            try {
                List<String> obfuscatedCode = obfuscator.obfuscate(file);

                try {
                    createFile(obfuscatedCode.get(1), Path.of(file.getParent() + "//" + obfuscatedCode.get(0) + ".java"));

                    String message = "Obfuscation of " + file.getName() + " has been completed. " +
                            "Obfuscated file can be found in " + file.getParent();
                    showAlert("Success", message, Alert.AlertType.INFORMATION);
                } catch (IOException e) {

                    // If 'createFiles' method couldn't create a file
                    MainController.logger.error("Error while creating obfuscated version of file " + file.getName());

                    String message = "Could not create obfuscated version of file " + file.getName();
                    showAlert("Error", message, Alert.AlertType.ERROR);
                }
            } catch (IOException e) {

                // If 'obfuscate' method couldn't read the content of file
                MainController.logger.error("Error while reading content of file " + file.getName());

                String message = "Could not read the content of file " + file.getName();
                showAlert("Error", message, Alert.AlertType.ERROR);
            } catch (IndexOutOfBoundsException e) {

                // If no name of main class in provided file was found
                MainController.logger.error("Error while obtaining the name of main Class in file " + file.getName());

                String message = "Could not obtain the name of main class in file " + file.getName();
                showAlert("Error", message, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Creates file with specified content and path
     *
     * @param content Content
     * @param path    Path
     * @throws IOException Can't create a file
     */
    private void createFile(String content, Path path) throws IOException {
        Files.write(path, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Opens a window for selecting files
     *
     * @param actionEvent The action event representing a button click
     */
    @FXML
    public void selectFiles(ActionEvent actionEvent) {
        // Creating a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Setting an extension filter to restrict file selection to Java files (*.java)
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java files", "*.java"));

        // Setting the title of the file chooser dialog
        fileChooser.setTitle("Select file(-s)");

        // Showing the file chooser dialog and get the selected files
        selectedFiles = fileChooser.showOpenMultipleDialog(MainApplication.mainStage);

        // If files are selected, enabling a button. In other case disable it
        if (selectedFiles != null) {
            obfuscationButton.setDisable(false);
        } else {
            obfuscationButton.setDisable(true);
        }
    }
}