module com.example.colat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.media;
    requires javafx.graphics;

    opens controller to javafx.fxml;
    exports controller;
}