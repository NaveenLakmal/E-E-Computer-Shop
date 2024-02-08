package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.AdditionalItemBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.AdditionalItemBoImpl;
import bo.custom.impl.OrderBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.AdditionalItemDao;
import dao.custom.impl.AdditionalItemDaoImpl;
import dao.util.BoType;
import dto.*;
import dto.tm.AdditionalItemTm;
import dto.tm.ItemTm;
import dto.tm.OrderTm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    public TableColumn colItemCodeT;
    public TableColumn colSubCategoryT;
    public TableView tblItem;
    public JFXTextField txtDescription1;
    public JFXComboBox cmbCategory;
    public JFXTextField txtSubCategory1;
    public JFXTextField txtSubItemPrice;
    public JFXTextField txtSubItemName;
    public TableColumn colSubItemName;
    public TableColumn colSubItemPrice;

    public JFXTextField txtQty;
    public TableColumn colSubItemQty;
    public JFXTextField txtOrderId;
    public Label lblTime;
    public Label lblDate;
    public ImageView imgOrderStatus;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    private CustomerBo customerBo = new CustomerBoImpl();
    private AdditionalItemBo itemsBo = new AdditionalItemBoImpl();
    private OrderBo orderBo = new OrderBoImpl();
    private AdditionalItemDao additionalItemDao = new AdditionalItemDaoImpl();
    private List<CustomerDto> customers;
    private List<AdditionalItemDto> items;

    private ObservableList<AdditionalItemTm> tmList = FXCollections.observableArrayList();

    public void initialize() {
        dateAndTime();

        ObservableList list = FXCollections.observableArrayList("Electronic", "Electrical");
        cmbCategory.setItems(list);


        try {
            customers = customerBo.allCustomer();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //loadItemCodes();
        loadCustomerIds();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (CustomerDto dto : customers) {
                if (dto.getCustId().equals(newValue.toString())) {
                    txtCustName.setText(dto.getCustName());
                }
            }
        });

//        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
//            for (AdditionalItemDto dto : items) {
//                if (dto.getItemCode().equals(newValue.toString())) {
//                    txtSubItemName.setText(dto.getName());
//                    txtSubItemPrice.setText(dto.getPrice() + "");
//                }
//            }
//        });

        setOrderId();


        //
        //*colItemCodeT.setCellValueFactory(new PropertyValueFactory<>("iCode"));
        // colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        //*colSubCategoryT.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        //colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        //colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        //loadItemTable();
        //
    }

    private void loadItemCodes() {
        ObservableList list = FXCollections.observableArrayList();

        for (AdditionalItemDto dto : items) {
            list.add(dto.getItemCode());
        }

        cmbItemCode.setItems(list);
    }

    private void loadCustomerIds() {
        ObservableList list = FXCollections.observableArrayList();

        for (CustomerDto dto : customers) {
            list.add(dto.getCustId());
        }

        cmbCustId.setItems(list);
    }

    private void setOrderId() {
        try {
            lblOrderId.setText(orderBo.generateId());
            //txtOrderId.setEditable(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBack(MouseEvent mouseEvent) {
        Stage stage = (Stage) imgBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBordForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemDto> dtoList  = itemBo.allItems();
            for (ItemDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #ff4d79;"); // Set the background color to red
                btn.setPrefWidth(90); // Set preferred width
                btn.setPrefHeight(32); // Set preferred height

                ItemTm c = new ItemTm(
                        dto.getItemCode(),
                        dto.getCategory(),
                        dto.getSubCategory(),
                        dto.getDescription(),
                        btn
                );

                /*btn.setOnAction(actionEvent -> {
                    deleteItem(c.getICode());
                });

                tmList.add(c);
            }
            tblItem.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void addToCartButtonOnAction(ActionEvent event) {
        JFXButton btn = new JFXButton("Delete");

        AdditionalItemTm tm = new AdditionalItemTm(
                cmbItemCode.getValue().toString(),
                txtSubItemName.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtSubItemPrice.getText()) * Integer.parseInt(txtQty.getText()),
                btn
        );
        btn.setOnAction(actionEvent -> {
            tmList.remove(tm);


            tblPlaceOrder.refresh();
        });

        boolean isExist = false;
        for (AdditionalItemTm order : tmList) {
            if (order.getItemCode().equals(tm.getItemCode())) {
                order.setQty(order.getQty() + tm.getQty());
                order.setPrice(order.getPrice() + tm.getPrice());
                isExist = true;
                System.out.println(isExist);
                //total += tm.getAmount();
            }
        }

        if (!isExist) {
            tmList.add(tm);
            tblPlaceOrder.setItems(tmList);
        }


    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
        List<OrderDetailDto> list = new ArrayList<>();

        for (AdditionalItemTm tm : tmList) {
            list.add(new OrderDetailDto(
                    lblOrderId.getText(),
                    tm.getItemCode(),
                    tm.getQty(),
                    tm.getPrice()
            ));
        }

        OrderDto dto = new OrderDto(
                lblOrderId.getText(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                cmbCustId.getValue().toString(),
                cmbCategory.getValue().toString(),
                txtSubCategory.getText(),
                txtDescription.getText(),
                0.00,     /*Total*/
                "Pending",
                list
        );


        try {
            boolean isSaved = orderBo.saveOrder(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Order Saved!").show();
                setOrderId();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

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

    public void goStatusForm(MouseEvent mouseEvent) {
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
    private void clearFields() {
        tblItem.refresh();
        txtCustName.clear();
        txtSubCategory.clear();
        txtDescription.clear();

    }


}
