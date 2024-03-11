module com.example.examen2evaldi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.examen2evaldi to javafx.fxml;
    exports com.example.examen2evaldi;
}