package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.CustomerDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CustomerFormController {
    public JFXTextField txtCustId;
    public ImageView imgBack;
    public TableView tblItem;
    public TableColumn colCustId;
    public TableColumn colCustName;
    public TableColumn colNumber;
    public TableColumn colEmail;
    public TableColumn colOption;
    public TextField txtSearch;
    public JFXTextField txtCustName;
    public JFXTextField txtNumber;
    public JFXTextField txtEmail;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void goBack(MouseEvent mouseEvent) {
    }

    public void searchButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {

    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        CustomerDto dto = new CustomerDto(txtCustId.getText(),
                txtCustName.getText(),
                txtNumber.getText(),
                txtEmail.getText()
        );

        try {
            boolean isSaved = customerBo.saveCustomer(dto);
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
