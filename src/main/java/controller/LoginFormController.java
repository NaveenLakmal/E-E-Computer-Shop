package controller;


import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;


import dao.util.BoType;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class LoginFormController {

    public JFXTextField txtItemCode;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXComboBox cmbBxSelectUser;

    //private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    private DashBordFormController dashBordFormController;

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

        UserDto dto = new UserDto(txtUserName.getText(), txtPassword.getText());

        try {
            String userType = userBo.loginCheck(dto);

            //

            //


            //System.out.println(userType);
            if (txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
                //new Alert(Alert.AlertType.ERROR, "user Name Input bar or Password Input Bar is Emty").show();
//                Stage stage = (Stage) txtUserName.getScene().getWindow();
//                stage.setTitle("Register Form");
//                try {
//                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"))));
//                    stage.centerOnScreen();
//                    stage.show();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            } else if (userType == null) {
                //System.out.println("check point");
                new Alert(Alert.AlertType.ERROR, "Invalid User Name Or Password...!!").show();
                // Handle the case where userType is null

//            } else if (!(userType == null)) {
//
//                Stage stage = (Stage) txtUserName.getScene().getWindow();
//                stage.setTitle("Register Form");
//                try {
//
//                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"))));
//                    stage.centerOnScreen();
//                    stage.show();
//
//                    //dashBordFormController.setUserType(userType);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
                } else if (userType.equalsIgnoreCase("Employee")) {

                Stage stage = (Stage) txtUserName.getScene().getWindow();
                stage.setTitle("Register Form");
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"))));
                    stage.centerOnScreen();
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println("employee");
                //new Alert(Alert.AlertType.INFORMATION,"vild User name or Password..!").show();
            } else if (userType.equalsIgnoreCase("Admin")) {
                //S/ystem.out.println("admin");
                //new Alert(Alert.AlertType.ERROR,"wrdvild User name or Password..!").show();
            } else if (userType.equalsIgnoreCase("Main Admin")) {
                Stage stage = (Stage) txtUserName.getScene().getWindow();
                stage.setTitle("Register Form");
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainAdminForm.fxml"))));
                    stage.centerOnScreen();
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //new Alert(Alert.AlertType.ERROR,"wrdvild User name or Password..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
