package neutron.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML
    TextField cTextField = new TextField();
    @FXML
    TextField pyTextField = new TextField();
    @FXML
    ColorPicker bgPicker = new ColorPicker();
    @FXML
    Spinner<Integer> fontSpinner = new Spinner<Integer>();


}
