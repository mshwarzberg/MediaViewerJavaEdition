package core;

import java.io.File;
import java.util.Arrays;

public enum FileType {
    VIDEO(new String[]{"mp4", "mkv", "avi", "webm"}),
    IMAGE(new String[]{"jpg", "jpeg", "png", "gif", "bmp", "wbmp", "webp"}),
    DOCUMENT(new String[]{"txt", "rtf"}),
    OTHER(new String[]{}),
    ;

    private final String[] extensions;

    FileType(String[] extensions) {
        this.extensions = extensions;
    }

    public static FileType getByExtension(File file) {
        String extension = getFileExtension(file);
        if (Arrays.asList(IMAGE.extensions).contains(extension)) {
            return IMAGE;
        }
        if (Arrays.asList(VIDEO.extensions).contains(extension)) {
            return VIDEO;
        }
        if (Arrays.asList(DOCUMENT.extensions).contains(extension)) {
            return DOCUMENT;
        }
        return OTHER;
    }

    private static String getFileExtension(File selectedFile) {
        if (selectedFile == null) {
            return "";
        }
        return selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
    }

    public boolean isType(String extension) {
        return Arrays.asList(this.extensions).contains(extension);
    }
}
