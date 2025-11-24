module com.example.colat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.media;

    opens controller to javafx.fxml;
    exports controller;
}