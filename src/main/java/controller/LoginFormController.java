package controller;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;



public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXComboBox cmbBxSelectUser;

    //private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void registerButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*public void signInButtonOnAction(ActionEvent actionEvent) {
        UserDto dto = new UserDto(
                txtUserName.getText(),
                txtPassword.getText(),

        );


            boolean isSaved = userBo.loginCheck(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                //loadCustomerTable();
                //clearFields();
            }




    }*/

    public void forgetPasswordButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ForgetPasswordForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void signInButtonOnAction(ActionEvent actionEvent) {
    }
}
