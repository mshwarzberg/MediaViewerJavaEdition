package window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
import tool.FileMetadata;
import tool.MyButton;

import java.util.ArrayList;
import java.util.List;

public class MetadataWindow extends Stage {
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 300;
    private final TitleBar titleBar;
    private final TabPane tabPane = new TabPane();
    private final Tab generalTab = new Tab("General");
    private final Tab descriptionTab = new Tab("Description");
    private final Tab tagsTab = new Tab("Tags");
    private final FileMetadata metadata;

    public MetadataWindow(FileMetadata metadata) {
        this.metadata = metadata;
        setup();
        tabPane.getTabs().addAll(generalTab, descriptionTab, tagsTab);
        titleBar = new TitleBar(metadata.getSourceFile());
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
        setWindowContent();
    }

    public void setup() {
        generalTab.setContent(new GeneralTab(metadata));
        generalTab.setStyle("-fx-text-fill: white");
        descriptionTab.setContent(new DescriptionTab(metadata));
        setTagsTab();
        tagsTab.setClosable(false);
        generalTab.setClosable(false);
        descriptionTab.setClosable(false);
    }

    private void setWindowContent() {
        Scene scene = new Scene(new VBox(titleBar, tabPane));
        scene.getStylesheets().add("style.css");
        setScene(scene);
        setTitle(metadata.getSourceFile());
        setWidth(WINDOW_WIDTH);
        setHeight(WINDOW_HEIGHT);
        show();
    }

    private void setTagsTab() {
        List<HBox> tagBoxes = new ArrayList<>();
        if (metadata.getTagsArray() != null) {
            for (String tag : metadata.getTagsArray()) {
                tagBoxes.add(new HBox(new MyButton("-"), new MyButton(tag)));
            }
        }
        if (metadata.getTagsString() != null) {
            tagBoxes.add(new HBox(new MyButton("-"), new MyButton(metadata.getTagsString())));
        }
        tagsTab.setContent(new TagPane(tagBoxes));
    }

    private static class TagPane extends FlowPane {
        TagPane(List<HBox> tagBoxes) {
            setHgap(5);
            setVgap(10);
            getChildren().addAll(tagBoxes);
            getChildren().add(new MyButton("+"));
            setPadding(new Insets(10, 10, 10, 10));
            VBox container = new VBox();
            container.getChildren().addAll(this);
        }
    }

    private static class GeneralTab extends ScrollPane {
        GeneralTab(FileMetadata metadata) {
            Label generalContent = new Label(metadata.getTooltipString());
            generalContent.setWrapText(true);
            generalContent.setPrefWidth(WINDOW_WIDTH - 20);
            setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            setContent(generalContent);
            setPadding(new Insets(10));
        }
    }

    private static class DescriptionTab extends VBox {
        DescriptionTab(FileMetadata metadata) {
            getChildren().addAll(new Headline(metadata.getHeadline()), new Description(metadata.getDescription()), new SaveButton());
            setPadding(new Insets(5, 5, 5, 5));
            setMargin(getChildren().get(2), new Insets(5, 0, 0, 0));
        }

        private static class Headline extends TextField {
            Headline(String text) {
                setText(text);
            }
        }

        private static class Description extends TextArea {
            Description(String text) {
                setText(text);
                setWrapText(true);
            }
        }

        private static class SaveButton extends MyButton {
            SaveButton() {
                super("Save");
                setAlignment(Pos.BASELINE_RIGHT);
                setDisable(true);
            }
        }
    }
}

