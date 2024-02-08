package controller;

import bo.BoFactory;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.AdditionalItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class StatusChangeFormController {

    public TableView tblOrders;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colItemName;
    public TableColumn colStatus;
    public JFXTextField txtOrderId;
    public ImageView imgBack;
    public JFXButton btnProcessing;

    private String currentStatus;

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ObservableList<AdditionalItemTm> tmList = FXCollections.observableArrayList();

    public void initialize() {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        loadOrderTable();


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
                        dto.getStatus()


                );

                tmList.add(c);

            }

            tblOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                setData((OrderDto) newValue);
            });


            tblOrders.setItems(tmList);
            // System.out.println("Load" + dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void setData(OrderDto newValue) {
        if (newValue != null) {
            txtOrderId.setText(newValue.getOrderId());
            currentStatus=newValue.getStatus();
        }
    }

    public void processingButtonOnAction(ActionEvent event) {
        //OrderDto dto = new OrderDto();
        //dto.setOrderId(txtOrderId.getText());
        //dto.setStatus("Processing");


        List<OrderDetailDto> list = new ArrayList<>();

        for (AdditionalItemTm tm : tmList) {
            list.add(new OrderDetailDto(
                    txtOrderId.getText(),
                    tm.getItemCode(),
                    tm.getQty(),
                    tm.getPrice()
            ));
        }

        OrderDto dto = new OrderDto();
        dto.setOrderId(txtOrderId.getText());
        dto.setStatus("Processing");
        dto.setList(list);


        try {
            if(!(currentStatus.equalsIgnoreCase("processing")) ) {
                if (!(currentStatus.equalsIgnoreCase("completed")) ){
                    boolean isUpdated = orderBo.updateStatus(dto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Status Updated!").show();
                    loadOrderTable();


                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrongbn!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void completedButtonOnAction(ActionEvent event) {


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
}
