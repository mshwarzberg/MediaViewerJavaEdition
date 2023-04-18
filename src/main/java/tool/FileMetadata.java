package tool;

import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.util.List;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileMetadata {
    private String fileName;

    private String sourceFile;
    private String fileSize;

    private String description;

    private List<String> tagsArray;
    private String tagsString;
    private String headline;
    private File file;

    public FileMetadata() {}

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
    }
}
