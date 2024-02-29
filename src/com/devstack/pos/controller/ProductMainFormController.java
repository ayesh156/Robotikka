package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.ProductBo;
import com.devstack.pos.bo.custom.impl.ProductBoImpl;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.dto.ProductDto;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.view.tm.ProductTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProductMainFormController {
    public AnchorPane context;
    public TextField txtSearch;
    public JFXTextField txtProductCode;
    public JFXTextArea txtProductDescription;
    public JFXButton btnSaveUpdate;
    public JFXTextField txtSelectedProdCode;
    public TableColumn colProductDesc;
    public TableColumn colProductShowMore;
    public TableColumn colProductDelete;

    public JFXTextArea txtSelectedProdDescription;
    public TableView<ProductTm> tbl;
    public TableColumn colProductId;
    public JFXButton btnNewBatch;

    ProductBo bo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    private String searchText = "";

    public void initialize() throws SQLException, ClassNotFoundException {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProductShowMore.setCellValueFactory(new PropertyValueFactory<>("showMore"));
        colProductDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        // ----- load new Product Id
        loadProductId();
        loadAllProduct(searchText);
        // ----- load new Product Id

        tbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setData(newValue);
        });

    }

    private void setData(ProductTm newValue) {
        txtSelectedProdCode.setText(String.valueOf(newValue.getCode()));
        txtSelectedProdDescription.setText(newValue.getDescription());
        btnNewBatch.setDisable(false);
    }

    private void loadProductId() {
        try {
            txtProductCode.setText(String.valueOf(bo.getLastProductId()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashboardForm");
    }

    public void btnNewProductOnAction(ActionEvent event) {
        try{
            if(btnSaveUpdate.getText().equals("Save Product")){
                if(bo.saveProduct(new ProductDto(Integer.parseInt(txtProductCode.getText()),txtProductDescription.getText()))){
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Saved!").show();
                    clearFields();
                    loadAllProduct(searchText);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            } else {
                if(bo.saveProduct(new ProductDto(Integer.parseInt(txtProductCode.getText()),txtProductDescription.getText()))){
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Updated!").show();
                    clearFields();
                    loadAllProduct(searchText);
                    //---------
                    btnSaveUpdate.setText("Save Product");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }


        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllProduct(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<ProductTm> tms = FXCollections.observableArrayList();
        for (ProductDto dto : bo.findAllProducts()){
            Button showMore = new Button("Show more");
            Button delete = new Button("Delete");
            ProductTm tm = new ProductTm(dto.getCode(), dto.getDescription(), showMore, delete);
            tms.add(tm);

        }
        tbl.setItems(tms);
    }

    private void clearFields() {
        txtProductCode.clear();
        txtProductDescription.clear();
        loadProductId();
    }


    public void btnAddNewProductOnAction(ActionEvent event) {
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
    }

    public void btnNewBatchOnAction(ActionEvent event) throws IOException {

        if(!txtSelectedProdCode.getText().isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("../view/NewBatchForm.fxml"));
            Parent parent = fxmlLoader.load();
            NewBatchFormController controller = fxmlLoader.getController();
            controller.setProductCode(Integer.parseInt(txtSelectedProdCode.getText()),txtSelectedProdDescription.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.centerOnScreen();
        }else{
            new Alert(Alert.AlertType.WARNING, "Please select a valid one!").show();
        }
    }
}