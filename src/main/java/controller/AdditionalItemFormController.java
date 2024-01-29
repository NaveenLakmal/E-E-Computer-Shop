package controller;

import bo.BoFactory;
import bo.custom.AdditionalItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.AdditionalItemDto;
import dto.tm.AdditionalItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class AdditionalItemFormController {

    public ImageView imgBack;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colCategory;
    public TableColumn colSubCategory;
    public TableColumn colDescription;
    public TableColumn colOption;
    //public JFXTextField txtdescription;
    public JFXTextField txtItemCode;
    //public JFXTextField txtDescription;
    //public JFXComboBox cmbCategory;
    //public JFXTextField txtSubCategory;
    public TextField txtSearch;
    public JFXTextField txtItemPrice;
    public JFXTextField txtItemName;
    public TableColumn colName;
    public TableColumn colPrice;

    private AdditionalItemBo additionalItemBo = BoFactory.getInstance().getBo(BoType.ADDITIONALITEM);

    public void initialize(){
        ObservableList list = FXCollections.observableArrayList("Electronic", "Electrical");

        //!cmbCategory.setItems(list);

        //
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData((AdditionalItemTm) newValue);
        });


        // Create the FilteredList and set it as the items for the TableView
        FilteredList<AdditionalItemTm> filteredList = new FilteredList<>(tblItem.getItems(), p -> true);

// Add a listener to the text property of the search field
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update the predicate based on the new text value
            filteredList.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items when the search field is empty
                }

                // Check if the item's properties contain the search text
                String lowerCaseFilter = newValue.toLowerCase();
                return item.getItemCode().toLowerCase().contains(lowerCaseFilter) ||
                        item.getName().toLowerCase().contains(lowerCaseFilter);
                // Add more fields as needed
            });
        });

// Bind the FilteredList to the TableView
        tblItem.setItems(filteredList);


        //
    }
    private void clearFields() {
        tblItem.refresh();
        txtItemCode.clear();
        txtItemName.clear();
        txtItemPrice.clear();
        txtItemCode.setEditable(true);
    }

    private void setData(AdditionalItemTm newValue) {
        if (newValue != null) {
            txtItemCode.setEditable(false);
            txtItemCode.setText(newValue.getItemCode());
            txtItemName.setText(newValue.getName());
            txtItemPrice.setText(newValue.getPrice()+"");
        }
    }

    private void deleteItem(String iCode) {

        try {
            boolean isDeleted = additionalItemBo.deleteItem(iCode);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadItemTable();
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

    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage)imgBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //
    private void loadItemTable() {
        ObservableList<AdditionalItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<AdditionalItemDto> dtoList  = additionalItemBo.allItems();
            for (AdditionalItemDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #ff4d79;"); // Set the background color to red
                btn.setPrefWidth(90); // Set preferred width
                btn.setPrefHeight(32); // Set preferred height

                AdditionalItemTm c = new AdditionalItemTm(
                        dto.getItemCode(),
                        dto.getName(),
                        dto.getPrice(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(c.getItemCode());
                });

                tmList.add(c);
            }
            tblItem.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //


    public void reloadButtonOnAction(ActionEvent actionEvent) {
        loadItemTable();
        tblItem.refresh();
        clearFields();
    }

    public void searchButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {

        AdditionalItemDto dto = new AdditionalItemDto(txtItemCode.getText(),
                txtItemName.getText(),
                Double.parseDouble(txtItemPrice.getText())
        );

        try {
            boolean isUpdated = additionalItemBo.updateItems(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Items "+dto.getItemCode()+" Updated!").show();
                loadItemTable();
                //clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

       // String categoryType = (String) cmbCategory.getValue();

        AdditionalItemDto dto = new AdditionalItemDto(txtItemCode.getText(),
                txtItemName.getText(),
                Double.parseDouble(txtItemPrice.getText())
        );

        try {
            boolean isSaved = additionalItemBo.saveItem(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadItemTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
