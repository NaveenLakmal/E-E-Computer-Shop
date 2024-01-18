package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.ItemDto;
import dto.tm.ItemTm;
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
import java.util.function.Predicate;

public class ItemFormController {

    public ImageView imgBack;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colCategory;
    public TableColumn colSubCategory;
    public TableColumn colDescription;
    public TableColumn colOption;
    //public JFXTextField txtdescription;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXComboBox cmbCategory;
    public JFXTextField txtSubCategory;
    public TextField txtSearch;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        ObservableList list = FXCollections.observableArrayList("Electronic", "Electrical");

        cmbCategory.setItems(list);

        //
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("iCode"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData((ItemTm) newValue);
        });


        // Create the FilteredList and set it as the items for the TableView
        FilteredList<ItemTm> filteredList = new FilteredList<>(tblItem.getItems(), p -> true);

// Add a listener to the text property of the search field
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update the predicate based on the new text value
            filteredList.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items when the search field is empty
                }

                // Check if the item's properties contain the search text
                String lowerCaseFilter = newValue.toLowerCase();
                return item.getICode().toLowerCase().contains(lowerCaseFilter) ||
                        item.getSubCategory().toLowerCase().contains(lowerCaseFilter) ||
                        item.getDescription().toLowerCase().contains(lowerCaseFilter);
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
        cmbCategory.setValue(null);
        txtSubCategory.clear();
        txtDescription.clear();
        txtItemCode.setEditable(true);
    }

    private void setData(ItemTm newValue) {
        if (newValue != null) {
            txtItemCode.setEditable(false);
            txtItemCode.setText(newValue.getICode());
            cmbCategory.setValue(newValue.getCategory().toString());
            txtSubCategory.setText(newValue.getSubCategory());
            txtDescription.setText(newValue.getDescription());
        }
    }

    private void deleteItem(String iCode) {

        try {
            boolean isDeleted = itemBo.deleteItem(iCode);
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
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //
    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemDto> dtoList  = itemBo.allItems();
            for (ItemDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                ItemTm c = new ItemTm(
                        dto.getItemCode(),
                        dto.getCategory(),
                        dto.getSubCategory(),
                        dto.getDescription(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(c.getICode());
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

        ItemDto dto = new ItemDto(txtItemCode.getText(),
                (String) cmbCategory.getValue(),
                txtSubCategory.getText(),
                txtDescription.getText()
        );

        try {
            boolean isUpdated = itemBo.updateItems(dto);
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
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
