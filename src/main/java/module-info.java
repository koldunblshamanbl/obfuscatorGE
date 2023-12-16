module com.example.obfuscatorge {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens com.example.obfuscatorge to javafx.fxml;
    exports com.example.obfuscatorge;
}