package FileDirectoryFormServer.RequestHandlers;

import FileDirectoryFormServer.Utility.Mocks.FileInfoMock;
import HttpServer.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class DirectoryTest {

    Directory directoryRequestHandler;
    Request request;
    String directoryToServe = ".";

    @Before
    public void setUp() throws Exception {
        this.request = new Request("GET", "/someDir/", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenDirectoryExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setDirectoryExists(true);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo);
        assertTrue("Can handle should return true when the directory exists.", this.directoryRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleWhenDirectoryDoesNotExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setDirectoryExists(false);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo);
        assertFalse("Can handle should return false when the directory doesn't exist.", this.directoryRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        this.request = new Request("POST", "/someDir/", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setDirectoryExists(true);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo);
        assertFalse("Can handle should return false when the directory doesn't exist.", this.directoryRequestHandler.canRespond(this.request));
    }

    @Test
    public void callsGetEntries() throws Exception {
        this.request = new Request("POST", "/someDir/", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        FileInfoMock fileInfo = new FileInfoMock();
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo);
        this.directoryRequestHandler.getResponse(request);
        assertEquals(1, fileInfo.getEntriesCalledCount);
    }
}
