package FileDirectoryFormServer.Utility;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class FileInfo {

    public File getFile(String parent, String file) {
        return new File(parent, file);
    }

    public String getContentType(String filename) {
        return new MimetypesFileTypeMap().getContentType(filename);
    }

    public InputStream getInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public boolean fileExists(String directory, String file) {
        File fileToServe = new File(directory, file);
        return fileToServe.exists() && fileToServe.isFile();
    }

    public boolean directoryExists(String directoryServing, String directory) {
        File directoryToServe = new File(directoryServing, directory);
        return directoryToServe.exists() && directoryToServe.isDirectory();
    }

    public String getRelativePath(String parentDir, String entry) {
        return new File(parentDir, entry).getPath();
    }

    public String[] getEntries(String parentDirectory, String directory) {
        return new File(parentDirectory, directory).list();
    }

}
