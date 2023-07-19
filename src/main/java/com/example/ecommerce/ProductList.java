package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ConstrainedColumnResizeBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private TableView<Product> productTable;

    public VBox createTable(ObservableList<Product> data){
        TableColumn id= new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

       // ObservableList<Product> data = FXCollections.observableArrayList();
        //data.add(new Product(2, "MI Power Bank", 999));
        //data.add(new Product(4, "Bubble Gun", 100));.

        productTable = new TableView<>();
        productTable.setItems(data);
        productTable.getColumns().addAll(id, name, price);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        VBox vBox= new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(productTable);
        return vBox;
    }

    public VBox getDummyTable(){
        ObservableList<Product> data = FXCollections.observableArrayList();
        data.add(new Product(5, "Bulb",280));
        data.add(new Product(6, "Face Roller",550));
        return createTable(data);
    }

    public VBox getAllProducts(){
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }

    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data){
        return createTable(data);
    }
}
