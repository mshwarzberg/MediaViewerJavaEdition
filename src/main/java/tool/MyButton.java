package tool;

import javafx.scene.control.Button;

public class MyButton extends Button {
    public MyButton(String text) {
        super(text);
        getStyleClass().add("customButton");
    }
}
