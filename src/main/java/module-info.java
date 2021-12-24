module group.gameoflife {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens group.gameoflife to javafx.fxml;
    exports group.gameoflife;
    exports group.gameoflife.UI;

    //comment those packages that are not required
    //...
    opens group.gameoflife.UI to javafx.fxml; //for combined project
    //opens group.gameoflife.SQL_GUI to javafx.fxml; //for SQL Only
    //opens group.gameoflife.TEXTDB_GUI to javafx.fxml; //for TextDB Only
    //...
}