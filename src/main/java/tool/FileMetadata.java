package tool;

import java.io.File;
import java.util.List;

import com.google.gson.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import window.MetadataWindow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FileMetadata extends VBox {
    private String fileName;
    private String sourceFile;
    private String fileSize;
    private String description;
    private List<String> tagsArray;
    private String tagsString;
    private String headline;
    private File file;

    public FileMetadata() {
        setPrefWidth(MetadataWindow.WINDOW_WIDTH - 40);
    }

    public String getFileName() {
        return fileName;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getDescription() {
        return description;
    }

    public String getHeadline() {
        return headline;
    }

    public File getFile() {
        return file;
    }

    public List<String> getTagsArray() {
        return tagsArray;
    }

    public String getTagsString() {
        return tagsString;
    }

    public static class FileMetadataDeserializer implements JsonDeserializer<FileMetadata> {
        @Override
        public FileMetadata deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            FileMetadata fileMetadata = new FileMetadata();
            JsonObject jsonObject = json.getAsJsonObject();
            buildTooltip(jsonObject, fileMetadata);
            fileMetadata.fileName = getString(jsonObject.get("FileName"));
            fileMetadata.sourceFile = getString(jsonObject.get("SourceFile"));
            fileMetadata.file = new File(fileMetadata.sourceFile);
            fileMetadata.fileSize = getString(jsonObject.get("FileSize"));
            fileMetadata.description = getString(jsonObject.get("Description"));
            fileMetadata.headline = getString(jsonObject.get("Headline"));
            JsonElement keywordsElement = jsonObject.get("Keywords");
            if (keywordsElement != null) {
                if (keywordsElement.isJsonArray()) {
                    fileMetadata.tagsArray = new ArrayList<>();
                    for (JsonElement keywordElement : keywordsElement.getAsJsonArray()) {
                        fileMetadata.tagsArray.add(keywordElement.getAsString());
                    }
                } else if (keywordsElement.isJsonPrimitive()) {
                    fileMetadata.tagsString = getString(keywordsElement);
                }
            }
            return fileMetadata;
        }

        private String getString(JsonElement element) {
            if (element == null) {
                return "";
            }
            return element.getAsString();
        }

        private void buildTooltip(JsonObject jsonObject, FileMetadata fileMetadata) {
            Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                String name = entry.getKey();
                JsonElement value = entry.getValue();
                HBox metadataBlock = new HBox();
                try {
                    metadataBlock.getChildren().addAll(new Label(name + ": "), new TextField(value.getAsString()));
                } catch (IllegalStateException e) {
                    metadataBlock.getChildren().addAll(new Label(name + ": "), new TextField(value.toString()));
                }
                HBox.setHgrow(metadataBlock.getChildren().get(1), Priority.ALWAYS);
                fileMetadata.getChildren().add(metadataBlock);
            }
        }
    }
}
