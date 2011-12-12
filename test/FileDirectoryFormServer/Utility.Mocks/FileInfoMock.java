package FileDirectoryFormServer.Utility.Mocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class FileInfoMock extends FileDirectoryFormServer.Utility.FileInfo {

    public int getFileCalledCount = 0;
    public boolean throwOnGetInputStream = false;
    public int getEntriesCalledCount = 0;

    @Override
    public File getFile(String parent, String filename) {
        this.getFileCalledCount++;
        return new FileMock(parent, filename);
    }

    @Override
    public InputStream getInputStream(File file) throws FileNotFoundException {
        if (this.throwOnGetInputStream) {
            throw new FileNotFoundException();
        }
        return null;
    }

    @Override
    public boolean fileExists(String directory, String file) {
        return this.isFileExists();
    }

    @Override
    public boolean directoryExists(String directoryServing, String directory) {
        return this.isDirectoryExists();
    }

    @Override
    public String[] getEntries(String parent, String dir) {
        this.getEntriesCalledCount++;
        return new String[]{"first", "second"};
    }

    public boolean isFileExists() {
        return this.fileExists;
    }

    public void setFileExists(boolean fileExists) {
        this.fileExists = fileExists;
    }

    public boolean isDirectoryExists() {
        return directoryExists;
    }

    public void setDirectoryExists(boolean directoryExists) {
        this.directoryExists = directoryExists;
    }

    private boolean fileExists = false;
    private boolean directoryExists = false;
}
