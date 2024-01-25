package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.CustomerTm;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class CustomerFormController {
    public JFXTextField txtCustId;
    public ImageView imgBack;

    public TableColumn colCustId;
    public TableColumn colCustName;
    public TableColumn colNumber;
    public TableColumn colEmail;
    public TableColumn colOption;
    public TextField txtSearch;
    public JFXTextField txtCustName;
    public JFXTextField txtNumber;
    public JFXTextField txtEmail;
    public TableView tblCustomer;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

        public void initialize(){
            colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
            colCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
            colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
            loadCustomerTable();

            tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                setData((CustomerTm) newValue);
            });


            // Create the FilteredList and set it as the items for the TableView
            FilteredList<CustomerTm> filteredList = new FilteredList<>(tblCustomer.getItems(), p -> true);

// Add a listener to the text property of the search field
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                // Update the predicate based on the new text value
                filteredList.setPredicate(customer -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all items when the search field is empty
                    }

                    // Check if the item's properties contain the search text
                    String lowerCaseFilter = newValue.toLowerCase();
                    return customer.getCustId().toLowerCase().contains(lowerCaseFilter) ||
                            customer.getCustName().toLowerCase().contains(lowerCaseFilter) ||
                            customer.getNumber().toLowerCase().contains(lowerCaseFilter);
                    // Add more fields as needed
                });
            });

            // Bind the FilteredList to the TableView
            tblCustomer.setItems(filteredList);


            //
        }

    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage)imgBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList  = customerBo.allCustomer();
            for (CustomerDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                    btn.setStyle("-fx-background-color: #ff4d79;"); // Set the background color to red
                    btn.setPrefWidth(90); // Set preferred width
                    btn.setPrefHeight(32); // Set preferred height
                CustomerTm c = new CustomerTm(
                        dto.getCustId(),
                        dto.getCustName(),
                        dto.getNumber(),
                        dto.getEmail(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getCustId());
                });

                tmList.add(c);
            }
            tblCustomer.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        tblCustomer.refresh();
        txtCustId.clear();
        txtCustName.clear();
        txtNumber.clear();
        txtEmail.clear();

        txtCustId.setEditable(true);
    }

    private void setData(CustomerTm newValue) {
        if (newValue != null) {
            txtCustId.setEditable(false);
            txtCustId.setText(newValue.getCustId());
            txtCustName.setText(newValue.getCustName());
            txtNumber.setText(newValue.getNumber());
            txtEmail.setText(newValue.getEmail());

        }
    }

    private void deleteCustomer(String custId) {
        try {
            boolean isDeleted = customerBo.deleteCustomer(custId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadCustomerTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            //new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            e.printStackTrace();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void searchButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        CustomerDto dto = new CustomerDto(txtCustId.getText(),
                txtCustName.getText(),
                txtNumber.getText(),
                txtEmail.getText()
        );

        try {
            boolean isUpdated = customerBo.updateCustomer(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Items "+dto.getCustId()+" Updated!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

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
                loadCustomerTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void reloadButtonOnAction(ActionEvent actionEvent) {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();
    }
}
