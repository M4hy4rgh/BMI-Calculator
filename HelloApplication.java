package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    //-----------------components and Fields-----------------
        Label weightLabel = new Label("Weight:");
        TextField weightTextField = new TextField();
        // validate weightTextField
        weightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([.,]\\d*)?")) {
                weightTextField.setText(oldValue);
            }
        });

        Label heightLabel = new Label("Height:");
        TextField heightTextField = new TextField();
        // validate heightTextField
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([.,]\\d*)?")) {
                heightTextField.setText(oldValue);
            }
        });


        RadioButton engBtn = new RadioButton("English");
        RadioButton metBtn = new RadioButton("Metric");
        HBox unitBox = new HBox(engBtn, metBtn);
        unitBox.setSpacing(10);
        unitBox.setAlignment(Pos.CENTER);
        unitBox.setPadding(new Insets(0, 0, 0, 50));
        ToggleGroup unitGroup = new ToggleGroup();
        engBtn.setToggleGroup(unitGroup);
        metBtn.setToggleGroup(unitGroup);

        Button calcBtn = new Button("Calculate");
        calcBtn.setPadding(new Insets(3, 40, 3, 40));
// set calcBtn margin to 10px
        GridPane.setMargin(calcBtn, new Insets(0, 0, 0, 50));

        Label bmiLabel = new Label("BMI:");
        TextField bmiTextField = new TextField();
        bmiTextField.setEditable(false);

        //---------------------------------layout---------------------------------
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.add(weightLabel, 0, 0);
        root.add(weightTextField, 1, 0);

        root.add(heightLabel, 0, 1);
        root.add(heightTextField, 1, 1);

        root.add(unitBox, 0, 2, 2, 1);

        root.add(calcBtn, 0, 3, 2, 1);
        root.setHalignment(calcBtn, HPos.CENTER);

        root.add(bmiLabel, 0, 5);
        root.add(bmiTextField, 1, 5);



        BorderPane secondLayout = new BorderPane();
        secondLayout.setCenter(root);
        secondLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE,
                new CornerRadii(60, true), new Insets(10, 10, 10, 10))));

        calcBtn.setOnAction(event -> {// calculate BMI and display it.

            String weight = weightTextField.getText().replace(",", ".");
            String height = heightTextField.getText().replace(",", ".");
            double bmi = 0;
            if (weight.isEmpty() || height.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a value for weight and height");
                alert.showAndWait();
            } else {
                if (engBtn.isSelected()) {
                    bmi = (Double.parseDouble(weight) / (Double.parseDouble(height) * Double.parseDouble(height))) * 703;
                } else if (metBtn.isSelected()) {
                    bmi = (Double.parseDouble(weight) / (Double.parseDouble(height) * Double.parseDouble(height))) * 10000;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please select a unit");
                    alert.showAndWait();
                }
            }
            //convert bmi to 2 decimal places
            bmi = Math.round(bmi * 100.0) / 100.0;

            //check if bmi and display it
            bmiTextField.setText(String.valueOf(bmi));
            if (bmi < 18.5) {
                bmiTextField.setText(bmi + " - Underweight");
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                bmiTextField.setText(bmi + " - Normal");
            } else if (bmi >= 25 && bmi <= 29.9) {
                bmiTextField.setText(bmi + " - Overweight");
            } else {
                bmiTextField.setText(bmi + " - Obese");
            }
        });

    //-----------------scene and stage-----------------
        Scene scene = new Scene(secondLayout, 600, 600);
        stage.setTitle("BMI Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}