package core;

import java.util.Arrays;

public enum FileType {
    VIDEO(new String[]{"mp4", "mkv", "avi"}),
    IMAGE(new String[]{"jpg", "jpeg", "png", "gif", "bmp", "wbmp"}),
    DOCUMENT(new String[]{"txt", "rtf"}),
    ;

    private final String[] extensions;

    FileType(String[] extensions) {
        this.extensions = extensions;
    }

    public boolean isType(String extension) {
        return Arrays.asList(this.extensions).contains(extension);
    }
}
