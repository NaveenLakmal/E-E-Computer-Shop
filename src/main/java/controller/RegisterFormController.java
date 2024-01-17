package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterFormController {
    public ImageView imgBack;
    public JFXComboBox cmdAccountType;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXTextField txtRePassword;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        ObservableList list = FXCollections.observableArrayList("Employee", "Admin", "Main Admin");

        cmdAccountType.setItems(list);
    }

    public void registerButtonOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();

        if (!password.equals(rePassword)) {
            // Passwords do not match, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("The entered passwords do not match. Please re-enter your passwords.");
            alert.showAndWait();
        } else {
            // Passwords match, create UserDto object
            String accountType = (String) cmdAccountType.getValue();

            //System.out.println(accountType);

            UserDto dto = new UserDto(email, password, accountType);
            // Perform further actions with the UserDto object if needed

            try {
                boolean isSaved = userBo.saveUser(dto);

                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }




    }

    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage)imgBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
