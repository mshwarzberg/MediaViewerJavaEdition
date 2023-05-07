package tool;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import org.apache.commons.io.IOUtils;

public class ExifToolScanner {
    private final String pathToScan;
    private static final String EXIFTOOL_PATH = "./src/main/resources/exiftool.exe";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(FileMetadata.class, new FileMetadata.FileMetadataDeserializer())
            .create();
    ;
    private List<FileMetadata> fileMetadataArray = new ArrayList<>();

    public ExifToolScanner(String pathToScan) {
        this.pathToScan = pathToScan;
    }

    public FileList scan() {
        try {
            Process process = new ProcessBuilder()
                    .command(EXIFTOOL_PATH, "-json", "-q", pathToScan)
                    .redirectErrorStream(true)
                    .start();
            String output = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
            fileMetadataArray = List.of(gson.fromJson(output, FileMetadata[].class));
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("ExifTool returned non-zero exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new FileList(fileMetadataArray);
    }

    public List<FileMetadata> getFileMetadataArray() {
        return fileMetadataArray;
    }
}

