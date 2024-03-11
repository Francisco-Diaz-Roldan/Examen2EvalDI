package com.example.examen2evaldi.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> comboSexo;
    @FXML
    private Spinner<Double> spTalla;
    @FXML
    private ComboBox<String> comboActividad;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Label labelInfo;
    @FXML
    private Spinner<Double> spPeso;
    @FXML
    private Spinner<Integer> spEdad;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Para el combo de Sexo
        ObservableList<String> opcionesSexo = FXCollections.observableArrayList();
        opcionesSexo.addAll("Masculino", "Femenino");

        comboSexo.setItems(opcionesSexo);
        comboSexo.getSelectionModel().selectFirst();

        // Para el spinner de Edad
        SpinnerValueFactory<Integer> edadFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 18);
        spEdad.setValueFactory(edadFactory);

        // Para el spinner de Talla
        SpinnerValueFactory<Double> tallaFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 3, 1.70, 0.01);
        spTalla.setValueFactory(tallaFactory);

        // Para el spinner de Peso
        SpinnerValueFactory<Double> pesoFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 120, 70);
        spPeso.setValueFactory(pesoFactory);

        // Para el combo de Actividad
        ObservableList<String> opcionesActividad = FXCollections.observableArrayList();
        opcionesActividad.addAll("Sedentario", "Moderado", "Activo", "Muy activo");
        comboActividad.setItems(opcionesActividad);
        comboActividad.getSelectionModel().select("Moderado");
    }

    @FXML
    protected void guardar() {
        try {
            String nombreCliente = txtNombre.getText();
            int edad = spEdad.getValue();
            double talla = spTalla.getValue();
            double peso = spPeso.getValue();
            String sexo = comboSexo.getValue();
            String actividad = comboActividad.getValue();

            // Validación de entradas
            if (nombreCliente.isEmpty() || edad < 0 || edad > 100 ||
                    talla < 0 || talla > 10 || peso < 0 || peso > 200) {
                throw new IllegalArgumentException("Por favor, ingrese valores válidos.");
            }

            double ger = calcularGER(edad, talla, sexo);
            double get = calcularGET(ger, actividad, sexo);

            labelInfo.setText("El cliente " + nombreCliente +
                    " tiene un GER de " + ger + " y un GET de " + get);
        } catch (Exception e) {
            // Manejo de excepciones
            labelInfo.setText("Error al guardar. Por favor rellena todos los campos.");
        }
    }


    private double calcularGER(int edad, double talla, String sexo) {
        double factorGenero = (sexo.equalsIgnoreCase("Masculino")) ?
                66.473 + (13.751 * talla) + (5.0033 * talla) - (6.755 * edad) :
                655.0955 + (9.463 * talla) + (1.8496 * talla) - (4.6756 * edad);
        return factorGenero;
    }

    private double calcularGET(double ger, String actividad, String sexo) {
        double factorActividad = 0.0;

        switch (actividad.toLowerCase()) {
            case "sedentario":
                factorActividad = (sexo.equalsIgnoreCase("Masculino")) ? 1.3 : 1.3;
                break;
            case "moderado":
                factorActividad = (sexo.equalsIgnoreCase("Masculino")) ? 1.6 : 1.5;
                break;
            case "activo":
                factorActividad = (sexo.equalsIgnoreCase("Masculino")) ? 1.7 : 1.6;
                break;
            case "muy activo":
                factorActividad = (sexo.equalsIgnoreCase("Masculino")) ? 2.1 : 1.9;
                break;
        }

        return ger * factorActividad;
    }

    @Deprecated
    public void printPDF(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/examendi", "root", "");
            JasperPrint jasperPrint = JasperFillManager.fillReport("Examendi.jasper",null, connection);
            SwingNode swingNode = new SwingNode();
            contenidoEnSwing(swingNode, jasperPrint);

            StackPane root = new StackPane();
            root.getChildren().add(swingNode);
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Examen_DI");
            primaryStage.setScene(scene);
            primaryStage.show();

            JRPdfExporter exp = new JRPdfExporter();
            exp.setExporterInput(new SimpleExporterInput(jasperPrint));
            exp.setExporterOutput(new SimpleOutputStreamExporterOutput("informeCLiente.pdf"));
            exp.setConfiguration(new SimplePdfExporterConfiguration());
            exp.exportReport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    private void contenidoEnSwing(final SwingNode swingNode, JasperPrint jasperPrint) {
        SwingUtilities.invokeLater(() -> {
            JRViewer viewer = new JRViewer(jasperPrint);
            swingNode.setContent(viewer);
        });
    }
}
