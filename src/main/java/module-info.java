module com.example.examen2evaldi {
    requires javafx.controls;
    requires javafx.fxml;

    // Añadido
    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jasperreports;
    requires javafx.swing;

    //Aqui habria que añadir las clases que se vayan a implementar
    opens com.example.examen2evaldi to javafx.fxml;
    opens com.example.examen2evaldi.controller to javafx.fxml;

    exports com.example.examen2evaldi;
    exports com.example.examen2evaldi.controller;
}