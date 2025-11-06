module com.example.colat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.colat to javafx.fxml;
    exports com.example.colat;
}