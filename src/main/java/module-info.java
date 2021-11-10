module com.mycompany.crudhinterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.persistence;

    opens com.mycompany.crudhinterface to javafx.fxml;
    opens models;
    exports com.mycompany.crudhinterface;
    
}
