module com.demo.demofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;


    opens com.demo.demofx to javafx.fxml;
    exports com.demo.demofx;
}