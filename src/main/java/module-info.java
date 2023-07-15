module your.module.name {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.media;

    requires org.apache.commons.io;
    requires com.google.gson;
    requires annotations;
    requires java.sql;
    requires javafx.swing;
    exports core;

    opens tool to  com.google.gson;
    exports tool;

    exports view;
    exports window;
    exports video;
}
