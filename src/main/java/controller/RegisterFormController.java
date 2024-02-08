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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFormController {
    public ImageView imgBack;
    public JFXComboBox cmdAccountType;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXTextField txtRePassword;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize() {
        ObservableList list = FXCollections.observableArrayList("Employee", "Admin", "Main Admin");

        cmdAccountType.setItems(list);
    }

    public void registerButtonOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();
        String accountType = (String) cmdAccountType.getValue();

        if (!password.equals(rePassword)) {
            // Passwords do not match, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("The entered passwords do not match. Please re-enter your passwords.");
            alert.showAndWait();
        } else {

            ///start


            if (isValidEmail(email)) {
                if (cmdAccountType != null) {

                    if (accountType != null && !accountType.isEmpty()) {

                        UserDto dto = new UserDto(email, password, accountType);
                        // Perform further actions with the UserDto object if needed

                        try {
                            boolean isSaved = userBo.saveUser(dto);

                            if (isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "User Created!").show();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid Account Type!").show();
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Email!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Email!").show();
            }


        }


        //   end

        // Passwords match, create UserDto object


        //System.out.println(accountType);


    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage) imgBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
