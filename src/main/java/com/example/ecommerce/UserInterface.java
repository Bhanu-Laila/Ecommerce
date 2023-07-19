package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;

    HBox footerBar;

    Button signInButton;

    Label welcomeLable;

    VBox body;

    Customer loggedInCustomer;

    String message;

    ProductList productList = new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    public BorderPane createContent(){
        BorderPane root=new BorderPane();
        root.setPrefSize(800,600);
        //root.getChildren().add(loginPage); //method to add nodes as children to pane
        root.setTop(headerBar);
        //root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);

        return root;
    }

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();

    }

    private void createHeaderBar(){
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");

        Button searchButton = new Button("search");

        signInButton = new Button("Sign In");
        welcomeLable = new Label();

        Button cartButton = new Button("Cart");

        headerBar = new HBox();
        headerBar.setPadding(new Insets(10));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(searchBar, searchButton, signInButton, cartButton, placeOrderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signInButton);
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setSpacing(10);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(itemsInCart==null){
                    showDialog("Please add some products in the cart to place order");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }
                int count = Order.placeMultipleOrders(loggedInCustomer, itemsInCart);
                if(count!=0){
                    showDialog("Order for "+count+" products placed successfully");
                }
                else{
                    showDialog("Order Failed!!");
                }
            }
        });

        placeOrderButton.setAlignment(Pos.CENTER);

    }


    private void createFooterBar(){

        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        footerBar = new HBox();
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product==null){
                    showDialog("Please select a product first to place order");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
               if(status==true){
                   showDialog("Order Placed Successfully");
               }
               else{
                   showDialog("Order Failed!!");
               }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product==null){
                    showDialog("Please select a product first to add it to order!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to the cart successfully");

            }
        });

    }
    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.setTitle(null);
        alert.showAndWait();
    }

    private GridPane createLoginPage() {
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("bhanulaila1998@gmail.com");
        userName.setPromptText("Type your user name here");
        PasswordField Password = new PasswordField();
        Password.setText("Bhanu@115");
        Password.setPromptText("Type your password here");

        Label messageLable = new Label("Hi");

        Button loginButton = new Button("login");

        loginPage = new GridPane();
        loginPage.setStyle(" -fx-background-color: pink;");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(Password, 1, 1);
        loginPage.add(messageLable, 0 , 3);
        loginPage.add(loginButton, 0, 2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = Password.getText();
                Login login = new Login();

                loggedInCustomer = login.customerLogin(name, pass);
                if(loggedInCustomer!=null){
                    messageLable.setText("Welcome " +loggedInCustomer.getName());
                    welcomeLable.setText("Welcome-" +loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLable);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);

                }
                else{
                    messageLable.setText("LogIn Failed !! please enter correct username and password");
                }

            }
        });

        return loginPage;

    }
}
