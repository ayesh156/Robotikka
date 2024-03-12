package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.CustomerBo;
import com.devstack.pos.bo.custom.LoyaltyCardBo;
import com.devstack.pos.bo.custom.OrderDetailsBo;
import com.devstack.pos.bo.custom.ProductDetailsBo;
import com.devstack.pos.bo.custom.impl.LoyaltyCardBoImpl;
import com.devstack.pos.dto.*;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.enums.CardType;
import com.devstack.pos.util.QrDataGenerator;
import com.devstack.pos.util.UserSessionData;
import com.devstack.pos.view.tm.CartTm;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
    public TableView<CartTm> tblCart;
    public TableColumn colCode;
    public TableColumn colDesc;
    public TableColumn colSelPrice;
    public TableColumn colDisc;
    public TableColumn colShowPrice;
    public TableColumn colQty;
    public TableColumn colTCost;
    public TableColumn colOperation;
    public Label lblTotal;

    CustomerBo bo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ProductDetailsBo productDetailsBo = BoFactory.getInstance().getBo(BoType.PRODUCT_DETAILS);
    private OrderDetailsBo orderDetailsBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAILS);
    private LoyaltyCardBo loyaltyCardBo = BoFactory.getInstance().getBo(BoType.LOYALTY_CARD);

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSelPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colDisc.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

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

    public void newLoyaltyOnAction(ActionEvent event) throws WriterException, IOException {
        try {
            double salary = Double.parseDouble(txtSalary.getText());

            CardType type = null;
            if (salary >= 100000) {
                type = CardType.PLATINUM;
            } else if (salary >= 50000) {
                type = CardType.GOLD;
            } else {
                type = CardType.SILVER;
            }

            String uniqueData = QrDataGenerator.generate(25);
            //---------------------------Gen QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BufferedImage bufferedImage =
                    MatrixToImageWriter.toBufferedImage(
                            qrCodeWriter.encode(
                                    uniqueData,
                                    BarcodeFormat.QR_CODE,
                                    160, 160
                            )
                    );

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", baos);
            byte[] arr = baos.toByteArray();

            if (urlNewLoyalty.getText().equals("+ New Loyalty")) {
                boolean isLoyaltyCardAssigned = loyaltyCardBo.saveLoyaltyData(
                        new LoyaltyCardDto(
                                new Random().nextInt(100001),
                                type,
                                Base64.encodeBase64String(arr),
                                txtEmail.getText()
                        )
                );
                if (isLoyaltyCardAssigned) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!!").show();
                    urlNewLoyalty.setText("Show Loyalty Card Info");
                }else {
                    new Alert(Alert.AlertType.WARNING, "Try Again Sortly!!").show();

                }

            } else {

            }


        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Try Again Sortly!!").show();
            throw new RuntimeException(e);
        }

    }

    public CardType determineCardType(double salary) {
        if (salary >= 100000) {
            return CardType.PLATINUM;
        } else if (salary >= 50000) {
            return CardType.GOLD;
        } else {
            return CardType.SILVER;
        }
    }

    public double calculateDiscountedPrice(double originalPrice, LoyaltyCardDto cardType) {
        double discountedPrice = originalPrice;
        switch (cardType.getCardType()) {
            case PLATINUM:
                discountedPrice *= 0.8; // 20% discount for platinum
                break;
            case GOLD:
                discountedPrice *= 0.9; // 10% discount for gold
                break;
            case SILVER:
                discountedPrice *= 0.95; // 5% discount for silver
                break;
        }
        return discountedPrice;
    }

    public void txtLoadProductOnAction(ActionEvent event) {
        try {
            ProductDetailsJoinDto p = productDetailsBo.findProductJoinDetails(
                    txtBarcode.getText()
            );
            if (p != null) {
                txtDescription.setText(p.getDescription());
                double salary = Double.parseDouble(txtSalary.getText());
                CardType type = determineCardType(salary);
                double sellingPrice = calculateDiscountedPrice(p.getDto().getSellingPrice(), loyaltyCardBo.searchLoyaltyCardType(txtEmail.getText()));
                txtSellingPrice.setText(String.valueOf(sellingPrice));
                txtShowPrice.setText(String.valueOf(p.getDto().getShowPrice()));
                txtQtyOnHand.setText(String.valueOf(p.getDto().getQtyOnHand()));
                txtBuyingPrice.setText(String.valueOf(p.getDto().getBuyingPrice()));
                txtQty.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Can't Find the Product!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Can't Find the Product!").show();
            throw new RuntimeException(e);
        }
    }


    ObservableList<CartTm> tms = FXCollections.observableArrayList();
    public void addToCart(ActionEvent event) throws SQLException, ClassNotFoundException {
        int qty = Integer.parseInt(txtQty.getText());
        double salary = Double.parseDouble(txtSalary.getText());
        CardType type = determineCardType(salary);
        double sellingPrice = calculateDiscountedPrice(Double.parseDouble(txtSellingPrice.getText()), loyaltyCardBo.searchLoyaltyCardType(txtEmail.getText()));

        double discount = Double.parseDouble(txtDiscount.getText());

        double totalCost = (qty * sellingPrice)-discount;
        CartTm selectedCartTm = isExists(txtBarcode.getText());

        if (selectedCartTm != null) {
            selectedCartTm.setQty(qty + selectedCartTm.getQty());
            selectedCartTm.setTotalCost(totalCost + selectedCartTm.getTotalCost());
            tblCart.refresh();
            txtQty.clear();
            txtBarcode.requestFocus();
        } else {
            Button btn = new Button("Remove");
            CartTm tm = new CartTm(
                    txtBarcode.getText(),
                    txtDescription.getText(),
                    discount,
                    sellingPrice,
                    Double.parseDouble(txtShowPrice.getText()),
                    qty,
                    totalCost,
                    btn
            );

            btn.setOnAction((e) -> {
                tms.remove(tm);
                tblCart.refresh();
                setTotal();
            });

            tms.add(tm);
            tblCart.setItems(tms);
        }
        clear();
        txtBarcode.requestFocus();
        setTotal();
    }

    private void clear() {
        txtDescription.clear();
        txtBarcode.clear();
        txtSellingPrice.clear();
        txtDiscount.clear();
        txtShowPrice.clear();
        txtQtyOnHand.clear();
        txtBuyingPrice.clear();
        txtQty.clear();
    }

    private CartTm isExists(String code) {
        for (CartTm tm : tms){
            if(tm.getCode().equals(code)) {
                return tm;
            }
        }
        return null;
    }

    private void setTotal(){
        double total = 0;
        for (CartTm tm: tms){
            total += tm.getTotalCost();
        }
        lblTotal.setText(total+" /=");
    }

    public void btnCompleteOrderOnAction(ActionEvent event) {

        ArrayList<ItemDetailsDto> dtoList = new ArrayList<>();
        double discount = 0;
        for(CartTm tm: tms){
            dtoList.add(new ItemDetailsDto(
                    tm.getCode(),
                    tm.getQty(),
                    tm.getDiscount(),
                    tm.getTotalCost()

            ));
            discount+=tm.getDiscount();
        }

        OrderDetailsDto dto = new OrderDetailsDto(
                new Random().nextInt(100001),
                new Date(),
                Double.parseDouble(lblTotal.getText().split(" /=")[0]),
                txtEmail.getText(),
                discount,
                UserSessionData.email,
                dtoList
        );

        try{
           if(orderDetailsBo.makeOrder(dto)){
               new Alert(Alert.AlertType.INFORMATION, "Complete Order!").show();
               clearAllFields();
           }else {
               new Alert(Alert.AlertType.WARNING, "Try Again!").show();
           }
        }catch (SQLException e){
           e.printStackTrace();
            new Alert(Alert.AlertType.WARNING, "Try Again!").show();

        }

    }

    private void clearAllFields() {
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
        tblCart.getItems().clear();
        lblTotal.setText("0.00 /=");
        clear();
    }
}
