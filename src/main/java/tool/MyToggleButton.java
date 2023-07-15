package tool;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.ToggleButton;

public class MyToggleButton extends ToggleButton {

    public MyToggleButton(String active, String inactive, BooleanProperty booleanProperty, Runnable doSomething) {
        super(booleanProperty.get() ? inactive : active);
        setOnAction(event -> {
            doSomething.run();
        });
        booleanProperty.addListener(event -> {
            setText(booleanProperty.get() ? inactive : active);
        });
        getStyleClass().add("customButton");
    }
}
