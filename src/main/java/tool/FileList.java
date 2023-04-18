package tool;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class FileList {
    private final List<FileMetadata> fileMetadataList;
    private final List<File> fileList;

    public FileList(List<FileMetadata> fileMetadataList) {
        this.fileMetadataList = sort(fileMetadataList);
        this.fileList = this.fileMetadataList.stream().map(FileMetadata::getFile).toList();
    }

    private static List<FileMetadata> sort(List<FileMetadata> unsortedList) {
        return unsortedList.stream().sorted((aMetadata, bMetadata) -> {
            File a = aMetadata.getFile();
            File b = bMetadata.getFile();

            String aLetters = a.getName().replaceAll("[^a-zA-Z]", "");
            String bLetters = b.getName().replaceAll("[^a-zA-Z]", "");


            int cmp = aLetters.compareToIgnoreCase(bLetters);
            if (cmp != 0) {
                return cmp;
            }

            int aNumbers = 0;
            int bNumbers = 0;

            try {
                aNumbers = Integer.parseInt(a.getName().replaceAll("[^\\d]", ""));
                bNumbers = Integer.parseInt(b.getName().replaceAll("[^\\d]", ""));
            } catch (NumberFormatException ignored) {
            }
            cmp = Integer.compare(aNumbers, bNumbers);
            if (cmp != 0) {
                return cmp;
            }
            return a.getName().compareToIgnoreCase(b.getName());
        }).toList();
    }

    @Nullable
    public FileMetadata getMetadata(File currentFile) {
        int index = fileList.indexOf(currentFile);
        return fileMetadataList.get(index);
    }

    public File getNext(File currentFile) {
        return fileList.get(Math.min(fileList.size() - 1, fileList.indexOf(currentFile) + 1));
    }

    public File getPrevious(File currentFile) {
       return fileList.get(Math.max(0, fileList.indexOf(currentFile) - 1));
    }

}
