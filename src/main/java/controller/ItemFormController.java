package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLIntegrityConstraintViolationException;

public class ItemFormController {

    public ImageView imgBack;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colCategory;
    public TableColumn colSubCategory;
    public TableColumn colDescription;
    public TableColumn colOption;
    public JFXTextField txtdescription;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXComboBox cmbCategory;
    public JFXTextField txtSubCategory;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        ObservableList list = FXCollections.observableArrayList("Electronic", "Electrical");

        cmbCategory.setItems(list);
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


    public void reloadButtonOnAction(ActionEvent actionEvent) {
    }

    public void searchButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

        String categoryType = (String) cmbCategory.getValue();

        ItemDto dto = new ItemDto(txtItemCode.getText(),
                categoryType,
                txtSubCategory.getText(),
                txtDescription.getText()
        );

        try {
            boolean isSaved = itemBo.saveItem(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                //loadCustomerTable();
                //clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
