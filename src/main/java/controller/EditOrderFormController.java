package controller;

import bo.BoFactory;
import bo.custom.AdditionalItemBo;
import bo.custom.CustomerBo;
import bo.custom.OrderBo;
import bo.custom.OrderDetailBo;
import bo.custom.impl.AdditionalItemBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.AdditionalItemDto;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.AdditionalItemTm;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditOrderFormController {
    public TableView tblOrders;
    public TableColumn colProduct;
    public TableColumn colOrderCode;
    public TableColumn colDate;
    public TableColumn colTotal;
    public JFXTextField txtDescription;
    public JFXTextField txtSubCategory;
    public JFXTextField txtOrderId;
    public TableColumn colDescription;
    public TableView tblOrderDetail;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colPrice;
    public ImageView imgBack;
    public JFXTextField txtSubItemPrice;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtSubItemName;
    public JFXTextField txtQty;
    public TableView tblPlaceOrder;
    public TableColumn colSubItemCode;
    public TableColumn colSubItemName;
    public TableColumn colSubItemPrice;
    public TableColumn colSubItemQty;
    public TableColumn colOption;
    public Label lblTotal;

    private double total = 0;
    private  double previousTotal;

    private List<AdditionalItemDto> items;
    private AdditionalItemBo itemsBo = new AdditionalItemBoImpl();

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);

    private ObservableList<AdditionalItemTm> tmList = FXCollections.observableArrayList();

    public void initialize() {
        colOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadOrderTable();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadOrderDetailTable();

        colSubItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSubItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSubItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSubItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData((OrderDto) newValue);
        });

        try {

            items = itemsBo.allItems();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadItemCodes();

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (AdditionalItemDto dto : items) {
                if (dto.getItemCode().equals(newValue.toString())) {
                    txtSubItemName.setText(dto.getName());
                    txtSubItemPrice.setText(dto.getPrice() + "");
                }
            }
        });
    }

    private void loadItemCodes() {
        ObservableList list = FXCollections.observableArrayList();

        for (AdditionalItemDto dto : items) {
            list.add(dto.getItemCode());
        }

        cmbItemCode.setItems(list);
    }

    private void setData(OrderDto newValue) {
        if (newValue != null) {

            txtOrderId.setEditable(false);
            txtOrderId.setText(newValue.getOrderId());
            txtSubCategory.setText(newValue.getSubCategory());
            txtDescription.setText(newValue.getDescription());
            lblTotal.setText(String.valueOf(newValue.getTotal()));
            previousTotal=newValue.getTotal();
            //txtEmail.setText(newValue.getEmail());
            loadOrderDetailTable();

        }
    }

    private void loadOrderTable() {
        ObservableList<OrderDto> tmList = FXCollections.observableArrayList();

        try {

            List<OrderDto> dtoList = orderBo.allOrder();



            for (OrderDto dto : dtoList) {

                OrderDto c = new OrderDto(
                        dto.getOrderId(),
                        dto.getDate(),
                        dto.getSubCategory(),
                        dto.getDescription(),
                        dto.getTotal()


                );

                    tmList.add(c);

            }


            tblOrders.setItems(tmList);
            // System.out.println("Load" + dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void loadOrderDetailTable(){

        ObservableList<OrderDetailDto> tmList = FXCollections.observableArrayList();

        try {

            List<OrderDetailDto> dtoList = orderDetailBo.allOrderDetails();
            // System.out.println("Load list" + dtoList);



            for (OrderDetailDto dto : dtoList) {
                if(txtOrderId.getText().equals(dto.getOrderId())) {
                    OrderDetailDto c = new OrderDetailDto(
                            dto.getOrderId(),
                            dto.getItemCode(),
                            dto.getQty(),
                            dto.getPrice()

                );
                // System.out.println("" + c);


                    tmList.add(c);
                }
            }
            tblOrderDetail.setItems(tmList);
            // System.out.println("Load" + dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void clearFields() {
        tblPlaceOrder.getItems().clear();
        cmbItemCode.setValue(null);
        txtSubItemName.clear();
        txtQty.clear();
        txtSubItemPrice.clear();
        txtSubCategory.clear();
        txtDescription.clear();

        //txtOrderId.setEditable(true);
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
            total -= tm.getPrice();
            lblTotal.setText(String.format("%.2f", total));
            tblPlaceOrder.refresh();
            tblOrders.refresh();
        });

        boolean isExist = false;
        for (AdditionalItemTm order : tmList) {
            if (order.getItemCode().equals(tm.getItemCode())) {
                order.setQty(order.getQty() + tm.getQty());
                order.setPrice(order.getPrice() + tm.getPrice());
                isExist = true;
                tblPlaceOrder.refresh();
                tblOrders.refresh();
                total += tm.getPrice();
            }
        }

        if (!isExist) {
            tmList.add(tm);
            total += tm.getPrice();
        }

        lblTotal.setText(String.format("%.2f", total+previousTotal));
        tblPlaceOrder.setItems(tmList);
        tblOrders.refresh();

    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        List<OrderDetailDto> list = new ArrayList<>();

        for (AdditionalItemTm tm : tmList) {
            list.add(new OrderDetailDto(
                    txtOrderId.getText(),
                    tm.getItemCode(),
                    tm.getQty(),
                    tm.getPrice()
            ));
        }

        OrderDto dto = new OrderDto(
                txtOrderId.getText(),
                /*LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),*/
                txtSubCategory.getText(),
                txtDescription.getText(),
                Double.parseDouble(lblTotal.getText()),
                list
        );


        try {
            boolean isUpdated = orderBo.updateOrder(dto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Order Updated!").show();
                loadOrderTable();
                loadOrderDetailTable();
                //clearFields();


            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
