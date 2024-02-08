package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBordFormController {
    public Label lblDate;
    public Label lblTime;


    public void initialize() {
        dateAndTime();
    }

    private void dateAndTime() {
        Timeline date = new Timeline((new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        )), new KeyFrame(Duration.seconds(1)));
        date.setCycleCount(Animation.INDEFINITE);
        date.play();

        Timeline time = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ), new KeyFrame(Duration.seconds(1)));

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void customerButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void itemsBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void ordersBtnOnAction(ActionEvent actionEvent) {
    }

    public void placeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logOutButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void additionalButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdditionalItemForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Register Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EditOrderForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stsChangeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) lblDate.getScene().getWindow();
        stage.setTitle("Status Change Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StatusChangeForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
