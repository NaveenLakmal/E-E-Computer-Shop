package controller;

import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public TableColumn colItemCode;
    public TableColumn colSubCategory;

    private CustomerBo customerBo = new CustomerBoImpl();
    private ItemBo itemsBo = new ItemBoImpl();
    private OrderBo orderBo= new OrderBoImpl();
    private ItemDao itemDao = new ItemDaoImpl();
    private List<CustomerDto> customers;
    private List<ItemDto> items;

    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            customers = customerBo.allCustomer();
            items = itemsBo.allItems();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadItemCodes();
        loadCustomerIds();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (CustomerDto dto:customers) {
                if (dto.getCustId().equals(newValue.toString())){
                    txtCustName.setText(dto.getCustName());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (ItemDto dto:items) {
                if (dto.getItemCode().equals(newValue.toString())){
                    txtDescription.setText(dto.getDescription());
                    txtSubCategory.setText(dto.getSubCategory());
                }
            }
        });

        setOrderId();
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

    public void addToCartButtonOnAction(ActionEvent event) {
        JFXButton btn = new JFXButton("Delete");

        OrderTm tm = new OrderTm(
                cmbItemCode.getValue().toString(),
                txtSubCategory.getText(),
                txtDescription.getText(),
                btn
        );
        btn.setOnAction(actionEvent -> {
            tmList.remove(tm);


            tblPlaceOrder.refresh();
        });
                            //        boolean isExist = false;
                            //        for (OrderTm order:tmList) {
                            //            if (order.getCode().equals(tm.getCode())){
                            //                order.setQty(order.getQty()+tm.getQty());
                            //                order.setAmount(order.getAmount()+tm.getAmount());
                            //                isExist = true;
                            //                total+= tm.getAmount();
                            //            }
                            //        }

            tmList.add(tm);
        tblPlaceOrder.setItems(tmList);






    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
        List<OrderDetailDto> list = new ArrayList<>();

        for (OrderTm tm:tmList) {
            list.add(new OrderDetailDto(
                    lblOrderId.getText(),
                    tm.getItemCode(),
                    tm.getDescription()
            ));
        }

        OrderDto dto = new OrderDto(
                lblOrderId.getText(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                cmbCustId.getValue().toString(),
                list
        );



        try {
            boolean isSaved = orderBo.saveOrder(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Order Saved!").show();
                setOrderId();
            }else{
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
