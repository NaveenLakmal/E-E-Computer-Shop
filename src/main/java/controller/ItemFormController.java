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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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

        //
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("iCode"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadItemTable();
        //
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
                //clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
