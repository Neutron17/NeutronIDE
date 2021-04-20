package neutron.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.passay.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    PasswordField passPassField = new PasswordField();
    @FXML
    TextField passTextField = new TextField();
    @FXML
    TextField unameField = new TextField();
    @FXML
    Label outLabel = new Label();
    @FXML
    Button randButton = new Button();
    @FXML
    RadioButton showPassRad = new RadioButton();

    @FXML
    void handleSubmit() {
        if (passPassField.getText().isEmpty() || passPassField.getText().contains(" ")
                || unameField.getText().isEmpty() || unameField.getText().contains(" ")) {
            outLabel.setText("Password or Username fields cannot be empty or contain any whitespaces");
            return;
        }
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8,40));
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase,1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase,1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit,1));
        rules.add(new WhitespaceRule());

        PasswordValidator validator = new PasswordValidator(rules);
        PasswordData password = new PasswordData(passPassField.getText());
        RuleResult result = validator.validate(password);

        if (result.isValid()) {
            System.out.println("Valid");
            outLabel.setText("Valid Password");
        } else {
            System.out.println("Not Valid ");
            outLabel.setText(""+validator.getMessages(result));
        }
    }
    @FXML
    void handleRandomPass() {
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String pass = passwordGenerator.generatePassword(10,alphabets,digits);
        if (passPassField.isDisabled()) {
            passTextField.setText(pass);
            return;
        }
        passPassField.setText(pass);
    }
    @FXML
    void handleShowPass() {
        if (showPassRad.isSelected()) {
            passPassField.setVisible(false);
            passPassField.setDisable(true);
            passTextField.setVisible(true);
            passTextField.setDisable(false);
            passTextField.setText(passPassField.getText());
        }else{
            passPassField.setVisible(true);
            passPassField.setDisable(false);
            passTextField.setVisible(false);
            passTextField.setDisable(true);
            passPassField.setText(passTextField.getText());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passTextField.setDisable(true);
        passTextField.setVisible(false);
        passPassField.setDisable(false);
        passPassField.setVisible(true);
        ImageView view = new ImageView(new Image("/images/random.jpeg"));
        view.setFitHeight(randButton.getPrefHeight());
        view.setFitWidth(randButton.getPrefWidth());
        randButton.setGraphic(view);
    }
}
