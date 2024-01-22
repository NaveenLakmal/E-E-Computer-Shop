package controller;

import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderBoImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;
import dto.CustomerDto;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class PlaceOrderFormController {


    public ImageView imgBack;
    public TableView tblPlaceOrder;
    public TableColumn colCustId;
    public TableColumn colCustName;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colOption;
    public JFXTextField txtSubCategory;
    public JFXComboBox cmbCustId;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtCustName;
    public JFXTextField txtDescription;
    public Label lblOrderId;

    private CustomerBo customerBo = new CustomerBoImpl();
    private ItemBo itemsBo = new ItemBoImpl();
    private OrderBo orderBo= new OrderBoImpl();
    private ItemDao itemDao = new ItemDaoImpl();
    private List<CustomerDto> customers;
    private List<ItemDto> items;

    public void initialize(){

        try {
            customers = customerBo.allCustomer();
            items = itemsBo.allItems();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadItemCodes();
        loadCustomerIds();
    }

    private void loadItemCodes() {
        ObservableList list = FXCollections.observableArrayList();

        for (ItemDto dto:items) {
            list.add(dto.getItemCode());
        }

        cmbItemCode.setItems(list);
    }

    private void loadCustomerIds() {
        ObservableList list = FXCollections.observableArrayList();

        for (CustomerDto dto:customers) {
            list.add(dto.getCustId());
        }

        cmbCustId.setItems(list);
    }

    private void setOrderId() {
        try {
            lblOrderId.setText(orderBo.generateId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBack(MouseEvent mouseEvent) {
    }

    public void addToCartButtonOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
    }
}
