package core;

import java.util.Arrays;

public enum FileType {
    VIDEO(new String[]{"mp4", "mkv", "avi"}),
    IMAGE(new String[]{"jpg", "jpeg", "png", "gif", "bmp", "wbmp"}),
    DOCUMENT(new String[]{"txt", "rtf"}),
    OTHER(new String[] {})
    ;

    private final String[] extensions;

    FileType(String[] extensions) {
        this.extensions = extensions;
    }

    public boolean isType(String extension) {
        return Arrays.asList(this.extensions).contains(extension);
    }

    public static FileType getByExtension(String extension) {
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
}
