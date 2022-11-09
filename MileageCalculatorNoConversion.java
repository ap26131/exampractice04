package exampractice04;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Author: Joseph Berrigan, Alex Perez
 * Date: 11/7/2022
 * last modified on: 11/09/22
 * Practice assignment 4
 * Name: MileageCalculatorNoConversion.java
 * Course: CSCI 1302
 * Description: updating MileageCalculatorNoConversion file
 */

public class MileageCalculatorNoConversion extends Application {
	// default values/strings
    private double txtWidth = 125.0;
    private String defaultCalc = String.format("%.2f", 0.00);
    private String defaultEntry = String.format("%.2f", 0.00);
    private String defaultMileage = "Miles";
    private String defaultCapacity = "Gallons";
    private String defaultResult = "MPG";
    private String altMileage = "Kilometers";
    private String altCapacity = "Liters";
    private String altResult = "L/100KM";

    // create UI components split by type
    private Button btnCalc = new Button("Calculate");
    private Button btnReset = new Button("Reset");

    private Label lblDistance = new Label(defaultMileage);
    private Label lblCapacity = new Label(defaultCapacity);
    private Label lblResult = new Label(defaultResult);
    private Label lblEffType = new Label("Efficiency Type");

    private TextField tfDistance = new TextField(defaultEntry);
    private TextField tfCapacity = new TextField(defaultEntry);
    private TextField tfResult = new TextField(defaultCalc);

    private GridPane mainPane = new GridPane();
    
    // Task 1: replace the two radiobuttons with a single combo box
	private ComboBox<String> cmb1 = new ComboBox<>();

    public void start(Stage primaryStage) {
    	
    	// Task 2: populate the combo box options using some of the already-created strings in the class
    	ObservableList<String> units = FXCollections.observableArrayList(defaultResult, altResult);
    	cmb1.getItems().addAll(units);
    	cmb1.setValue(defaultResult);
    	
        // set preferences for UI components
        tfDistance.setMaxWidth(txtWidth);
        tfCapacity.setMaxWidth(txtWidth);
        tfResult.setMaxWidth(txtWidth);
        tfResult.setEditable(false);
     
        // create a main grid pane to hold items
        mainPane.setPadding(new Insets(10.0));
        mainPane.setHgap(txtWidth/2.0);
        mainPane.setVgap(txtWidth/12.0);

        // add items to mainPane
        mainPane.add(lblEffType, 0, 0);
        mainPane.add(new Label("Selection Type:"), 0, 1);
        mainPane.add(cmb1, 1, 1);
        mainPane.add(lblDistance, 0, 2);
        mainPane.add(tfDistance, 1, 2);
        mainPane.add(lblCapacity, 0, 3);
        mainPane.add(tfCapacity, 1, 3);
        mainPane.add(lblResult, 0, 4);
        mainPane.add(tfResult, 1, 4);
        mainPane.add(btnReset, 0, 5);
        mainPane.add(btnCalc, 1, 5);

        // register action handlers
        btnCalc.setOnAction(e -> calcMileage());
        tfDistance.setOnAction(e -> calcMileage());
        tfCapacity.setOnAction(e -> calcMileage());
        tfResult.setOnAction(e -> calcMileage());
        btnReset.setOnAction(e -> resetForm());
        cmb1.getSelectionModel().selectedItemProperty().addListener(e -> changeLabels());

        // create a scene and place it in the stage
        Scene scene = new Scene(mainPane);

        // set and show stage
        primaryStage.setTitle("Mileage Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();

        // stick default focus in first field for usability
        tfDistance.requestFocus();
    }

    /**
     * Convert existing figures and recalculate
     * This needs to be separate to avoid converting when
     * the conversion is not necessary
     */
    private void changeLabels() {
    	// distinguish between L/100KM and MPG
   	if (cmb1.getValue().equals(cmb1.getItems().get(0))) {
        	// update labels
        	lblCapacity.setText(altCapacity);
        	lblDistance.setText(altMileage);
        	lblResult.setText(altResult);
         } else {
        	// update labels
        	lblCapacity.setText(defaultCapacity);
        	lblDistance.setText(defaultMileage);
        	lblResult.setText(defaultResult);
        }
    }

    /**
     * Calculate expenses based on entered figures
     */
    private void calcMileage() {
    	// set default values
        double distance = 0.0, capacity = 0.0;

        // make sure to get numeric values only
        if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty()
        		&& tfDistance.getText() != null && !tfDistance.getText().isEmpty()) {
        	distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
        }

        // check for type of calculation
        double result = 0.0;
        if (cmb1.getValue().equals(cmb1.getItems().get(1))) {
        	// liters / 100KM
        	result = (distance != 0) ? capacity/(distance/100.0) : 0;
        } else {
        	// MPG
        	result = (capacity != 0) ? distance/capacity : 0;
        }

	    // update calculation fields with currency formatting
        tfResult.setText(String.format("%.2f", result));
    }

    /**
     * Reset all values in the application
     */
    private void resetForm() {
        // reset all form fields
    	cmb1.getItems().get(0);	
    	cmb1.setValue(defaultResult);
        tfDistance.setText(defaultEntry);
        tfCapacity.setText(defaultEntry);
        tfResult.setText(defaultCalc);
        lblCapacity.setText(defaultCapacity);
    	lblDistance.setText(defaultMileage);
    	lblResult.setText(defaultResult);
    }

	public static void main(String[] args) {
		launch(args);
	}
}
