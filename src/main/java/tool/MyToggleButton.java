package tool;

import javafx.scene.control.ToggleButton;

public class MyToggleButton extends ToggleButton {
    public MyToggleButton(String text) {
        super(text);
        getStyleClass().add("customButton");
    }
}
