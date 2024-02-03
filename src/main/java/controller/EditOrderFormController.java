package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderBo;
import bo.custom.OrderDetailBo;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.OrderDto;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
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

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    public void initialize() {
        colOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        //colTotal.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadOrderTable();

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData((CustomerTm) newValue);
        });
    }

    private void setData(CustomerTm newValue) {
//        if (newValue != null) {
//            txtCustId.setEditable(false);
//            txtCustId.setText(newValue.getCustId());
//            txtCustName.setText(newValue.getCustName());
//            txtNumber.setText(newValue.getNumber());
//            txtEmail.setText(newValue.getEmail());
//
//        }
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
                        dto.getDescription()


                );
                tmList.add(c);
            }


            tblOrders.setItems(tmList);
            // System.out.println("Load" + dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
