package com.example.demo9;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private String currentCustomerName;
    private List<Product> products = new ArrayList<>();
    private List<CartItem> cartItems = new ArrayList<>();
    private final String PRODUCTS_FILE = "products.txt";
    private final String USERS_FILE = "users.txt";
    private final String DELIMITER = ",";

    @Override
    public void start(Stage stage) throws Exception {
        loadProducts();
        showLoginPage(stage);
    }


    private void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(DELIMITER);

                try {
                    String id = fields[0];
                    String name = fields[1];
                    double price = Double.parseDouble(fields[2]);
                    String imagePath = fields[3];

                    products.add(new Product(id, name, price, imagePath));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveProducts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {
            for (Product product : products) {
                writer.write(product.getId() + DELIMITER + product.getName() + DELIMITER + product.getPrice() + DELIMITER + product.getImagePath());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        saveProducts();
    }

    private void showLoginPage(Stage stage) {
        GridPane login = new GridPane();
        stage.setTitle("Login Page");
        login.setAlignment(Pos.CENTER);
        login.setPadding(new Insets(20));
        login.setVgap(10);
        login.setHgap(10);
        Image icon = new Image("logo.jpg");
        stage.getIcons().add(icon);

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\OneDrive\\Documents\\New folder\\OIP.jpg");
            Image image = new Image(fis);
            BackgroundImage ni = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            Background b = new Background(ni);
            login.setBackground(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Label loginLabel = new Label("Welcome");
        loginLabel.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 16));
        loginLabel.setTextFill(Color.BLACK);
        login.add(loginLabel, 1, 0);

        Label userName = new Label("Enter the username");
        userName.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        login.add(userName, 0, 1);
        TextField nameField = new TextField();
        login.add(nameField, 1, 1);

        Label passwordLabel = new Label("Enter your password");
        passwordLabel.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        login.add(passwordLabel, 0, 2);
        PasswordField passField = new PasswordField();
        login.add(passField, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        //loginButton.setTextFill(Color.LIGHTPINK);
        login.add(loginButton, 1, 3);

        Label messageLabel = new Label();
        login.add(messageLabel, 1, 4);

        loginButton.setOnAction(event -> {
            String username = nameField.getText();
            String password = passField.getText();

            if (isValidCredentials(username, password)) {
                currentCustomerName = username;
                showProductsPage(stage);
            } else {
                messageLabel.setText("Invalid username or password.");
                messageLabel.setTextFill(Color.RED);
            }
        });

        Label haveNoAccount = new Label("Don't have an Account?");
        haveNoAccount.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        login.add(haveNoAccount, 0, 5);
        Button signinButton = new Button("Sign up");
        signinButton.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        login.add(signinButton, 1, 5);

        signinButton.setOnAction(e -> showSignupPage(stage));

        Scene scene = new Scene(login, 400, 400, Color.GRAY);
        stage.setTitle("Cosmetics Shop");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + DELIMITER + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showSignupPage(Stage stage) {
        GridPane signup = new GridPane();
        stage.setTitle("Signup Page");
        signup.setAlignment(Pos.CENTER);
        signup.setVgap(20);
        signup.setHgap(20);
        signup.setPadding(new Insets(20));

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\OneDrive\\Documents\\New folder\\OIP.jpg");
            Image image = new Image(fis);
            BackgroundImage ni = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            Background b = new Background(ni);
            signup.setBackground(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Label userName = new Label("Enter the username");
        userName.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(userName, 0, 2);

        TextField nameField = new TextField();
        signup.add(nameField, 1, 2);

        Label passwordLabel = new Label("Enter your password");
        passwordLabel.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(passwordLabel, 0, 3);

        PasswordField passField = new PasswordField();
        signup.add(passField, 1, 3);

        Label conpasswordLabel = new Label("Enter your confirm password");
        conpasswordLabel.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(conpasswordLabel, 0, 4);

        PasswordField conpassField = new PasswordField();
        signup.add(conpassField, 1, 4);

        Label gender = new Label("What is your Gender?");
        gender.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(gender, 0, 5);

        RadioButton m = new RadioButton("Male");
        m.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        RadioButton f = new RadioButton("Female");
        f.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(m, f);
        signup.add(hBox, 1, 5);

        ToggleGroup genderGroup = new ToggleGroup();
        m.setToggleGroup(genderGroup);
        f.setToggleGroup(genderGroup);

        Button signupButton = new Button("Signup");
        signupButton.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(signupButton, 1, 6);

        Label haveNoAccount = new Label("Already have an Account?");
        haveNoAccount.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(haveNoAccount, 0, 7);

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,12));
        signup.add(loginButton, 1, 7);

        Label messageLabel = new Label();
        signup.add(messageLabel, 1, 8);

        signupButton.setOnAction(e -> {
            String username = nameField.getText();
            String password = passField.getText();
            String confirmPassword = conpassField.getText();

            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match. Please try again.");
            } else {
                if (userExists(username)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User already exists.");
                } else {
                    addUserToFile(username, password);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully. Please login.");
                    showLoginPage(stage);
                }
            }
        });

        loginButton.setOnAction(e -> showLoginPage(stage));

        Scene scene = new Scene(signup, 400, 400, Color.LIGHTPINK);
        stage.setScene(scene);
        stage.show();
    }

    private void showProductsPage(Stage stage) {
        GridPane productsPage = new GridPane();
        stage.setTitle("Products page");
        productsPage.setAlignment(Pos.CENTER);
        productsPage.setPadding(new Insets(20));
        productsPage.setHgap(10);
        productsPage.setVgap(40);

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\Downloads\\OIP.jpg");
            Image image = new Image(fis);
            BackgroundImage ni = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            Background b = new Background(ni);
            productsPage.setBackground(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Label cosmetics = new Label("Cosmetics Shop");
        cosmetics.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        cosmetics.setTextFill(Color.DEEPPINK);
        productsPage.add(cosmetics, 0, 0);

        Button viewProduct = new Button("View all products");
        productsPage.add(viewProduct, 0, 1);
        viewProduct.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        viewProduct.setTextFill(Color.DEEPPINK);


        Button totalBill = new Button("Total bill");
        totalBill.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        totalBill.setTextFill(Color.DEEPPINK);
        productsPage.add(totalBill, 0, 2);

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        exit.setTextFill(Color.DEEPPINK);
        productsPage.add(exit, 0, 3);

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        backButton.setTextFill(Color.DEEPPINK);
        productsPage.add(backButton, 0, 4);
        backButton.setOnAction(e -> showLoginPage(stage));

        viewProduct.setOnAction(e -> showProductsListPage(stage));
        totalBill.setOnAction(e -> generateReceipt(stage));
        exit.setOnAction(e -> stage.close());

        Scene scene = new Scene(productsPage, 400, 400, Color.GRAY);
        stage.setScene(scene);
        stage.show();
    }


    private void showProductsListPage(Stage stage) {
        VBox productsListPage = new VBox(20);
        productsListPage.setPadding(new Insets(20));

        FlowPane productsFlowPane = new FlowPane();
        productsFlowPane.setHgap(30);
        productsFlowPane.setVgap(20);
        productsFlowPane.setAlignment(Pos.CENTER);
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\Downloads\\background.jpg");
            Image image = new Image(fis);
            BackgroundImage ni = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            Background b = new Background(ni);
            productsListPage.setBackground(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Product product : products) {
            Image image = new Image(product.getImagePath());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            Label nameLabel = new Label(product.getName());
            nameLabel.setMaxWidth(100);
            nameLabel.setWrapText(true);
            nameLabel.setAlignment(Pos.CENTER);

            Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));

            Spinner<Integer> quantitySpinner = new Spinner<>();
            quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

            Button addToCartButton = new Button("Add to Cart");
            addToCartButton.setOnAction(e -> {
                int quantity = quantitySpinner.getValue();
                cartItems.add(new CartItem(product.getId(), quantity));
                showAlert(Alert.AlertType.INFORMATION, "Info", "Added to cart: " + product.getName() + " x " + quantity);
            });

            VBox productBox = new VBox(10);
            productBox.setAlignment(Pos.CENTER);
            productBox.getChildren().addAll(imageView, nameLabel, priceLabel, quantitySpinner, addToCartButton);

            productsFlowPane.getChildren().add(productBox);
        }

        ScrollPane scrollPane = new ScrollPane(productsFlowPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Button backButton = new Button("Back");
        Button checkoutButton = new Button("Checkout");

        backButton.setOnAction(e -> showProductsPage(stage));
        checkoutButton.setOnAction(e -> generateReceipt(stage));

        HBox buttonsBox = new HBox(20);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(backButton, checkoutButton);

        productsListPage.getChildren().addAll(scrollPane, buttonsBox);

        Scene scene = new Scene(productsListPage, 600, 700);
        stage.setScene(scene);
        stage.show();
    }



    private Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public void generateReceipt(Stage stage) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Label receiptLabel = new Label("Receipt");
        receiptLabel.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,16));
        vbox.getChildren().add(receiptLabel);

        double total = 0;

        Label customerLabel = new Label("Customer: " + currentCustomerName);
        customerLabel.setFont(Font.font("TimesNewRoman",FontWeight.NORMAL,14));
        vbox.getChildren().add(customerLabel);

        vbox.getChildren().add(new Label(""));
        for (CartItem item : cartItems) {
            Product product = getProductById(item.getProductId());
            if (product != null) {
                total += product.getPrice() * item.getQuantity();
                Label productLabel = new Label("Product: " + product.getName());
                Label quantityLabel = new Label("Quantity: " + item.getQuantity());
                Label priceLabel = new Label("Price: $" + (product.getPrice() * item.getQuantity()));

                productLabel.setFont(Font.font("TimesNewRoman",FontWeight.NORMAL,14));
                quantityLabel.setFont(Font.font("TimesNewRoman",FontWeight.NORMAL,14));
                priceLabel.setFont(Font.font("TimesNewRoman",FontWeight.NORMAL,14));

                vbox.getChildren().addAll(productLabel, quantityLabel, priceLabel, new Label(""));
            }
        }

        Label totalLabel = new Label("Total: $" + total);
        totalLabel.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,16));
        vbox.getChildren().add(totalLabel);


        Scene scene = new Scene(vbox, 300, 400);
        Stage receiptStage = new Stage();
        receiptStage.setTitle("Receipt");
        receiptStage.setScene(scene);
        receiptStage.show();
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


class Product {
    private String id;
    private String name;
    private double price;
    private String imagePath;

    public Product(String id, String name, double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

}


class CartItem {
    private String productId;
    private int quantity;

    public CartItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

}
