package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswordFormController {
    public JFXTextField txtPassword;
    public JFXTextField txtConfirmPassword;
    public JFXTextField txtOtp;
    public ImageView imgBack;

    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage)imgBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void resetButtonOnAction(ActionEvent actionEvent) {
    }

    public void reciveButtonOnAction(ActionEvent actionEvent) {
    }

    public void txtPassword(ActionEvent actionEvent) {
    }

    public void txtConfirmPassword(ActionEvent actionEvent) {
    }

    public void txtOtp(ActionEvent actionEvent) {
    }
}
