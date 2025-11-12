module com.example.colat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens controller to javafx.fxml;
    exports controller;
}