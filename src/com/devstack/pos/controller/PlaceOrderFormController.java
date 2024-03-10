package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.CustomerBo;
import com.devstack.pos.bo.custom.ProductDetailsBo;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.dto.ProductDetailsJoinDto;
import com.devstack.pos.enums.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PlaceOrderFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public Label lblLoyaltyType;
    public Hyperlink urlNewLoyalty;
    public TextField txtDescription;
    public TextField txtBarcode;
    public TextField txtSellingPrice;
    public TextField txtDiscount;
    public TextField txtShowPrice;
    public TextField txtQtyOnHand;
    public TextField txtBuyingPrice;
    public TextField txtQty;
    public Label lblDiscountAvl;

    CustomerBo bo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ProductDetailsBo productDetailsBo = BoFactory.getInstance().getBo(BoType.PRODUCT_DETAILS);


    public void btnAddNewProductOnAction(ActionEvent event) throws IOException {
        setUi("ProductMainForm", true);
    }

    public void btnAddNewCustomerOnAction(ActionEvent event) throws IOException {
        setUi("CustomerForm", true);
    }

    public void btnBackToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashboardForm", false);
    }

    private void setUi(String url, boolean state) throws IOException {
        Stage stage = null;
        Scene scene =
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")));


        if(state){
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }else {
            stage = (Stage) context.getScene().getWindow();
            stage.setScene(scene);
        }
        stage.centerOnScreen();
    }

    public void txtSearchCustomerOnAction(ActionEvent event) {
        try {
            CustomerDto customer = bo.findCustomer(txtEmail.getText());

            if (customer!=null){

                txtName.setText(customer.getName());
                txtSalary.setText(String.valueOf(customer.getSalary()));
                txtContact.setText(customer.getContact());
                
                fetchLoyaltyCardData(txtEmail.getText());

            }else {
                new Alert(Alert.AlertType.WARNING, "Can't Find the Customer!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Can't Find the Customer!").show();
            throw new RuntimeException(e);
        }
    }

    private void fetchLoyaltyCardData(String text) {
        urlNewLoyalty.setText("+ New Loyalty");
        urlNewLoyalty.setVisible(true);
    }

    public void newLoyaltyOnAction(ActionEvent event) {
    }

    public void txtLoadProductOnAction(ActionEvent event) {

        try {
            ProductDetailsJoinDto p = productDetailsBo.findProductJoinDetails(
                    txtBarcode.getText()
            );
            if (p!=null){
                txtDescription.setText(p.getDescription());
                txtDiscount.setText(String.valueOf(0));
                txtSellingPrice.setText(String.valueOf(p.getDto().getSellingPrice()));
                txtShowPrice.setText(String.valueOf(p.getDto().getShowPrice()));
                txtQtyOnHand.setText(String.valueOf(p.getDto().getQtyOnHand()));
                txtBuyingPrice.setText(String.valueOf(p.getDto().getBuyingPrice()));
                txtQty.requestFocus();
            }else {
                new Alert(Alert.AlertType.WARNING, "Can't Find the Product!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Can't Find the Product!").show();
            throw new RuntimeException(e);
        }

    }
}
