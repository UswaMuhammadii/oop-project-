module com.example.demo9 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.demo9 to javafx.fxml;
    exports com.example.demo9;
}