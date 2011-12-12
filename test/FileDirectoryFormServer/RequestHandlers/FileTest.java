package FileDirectoryFormServer.RequestHandlers;

import FileDirectoryFormServer.Utility.Mocks.FileInfoMock;
import HttpServer.Exceptions.ResponseException;
import HttpServer.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class FileTest {

    File fileRequestHandler;
    Request request;
    String directoryToServe = ".";

    @Before
    public void setUp() throws Exception {
        this.request = new Request("GET", "/someFile.html", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenFileExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        assertTrue("Can handle should return true when the file exists.", this.fileRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleWhenFileDoesNotExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(false);
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        this.request = new Request("POST", "/someFile.html", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canRespond(this.request));
    }

    @Test
    public void getsTheFile() throws Exception {
        this.request = new Request("POST", "/someFile.html", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        FileInfoMock fileInfo = new FileInfoMock();
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        this.fileRequestHandler.getResponse(request);
        assertEquals(1, fileInfo.getFileCalledCount);
    }

    @Test(expected = ResponseException.class)
    public void throwsWhenFileDoesNotExist() throws Exception {
        this.request = new Request("POST", "/someFile.html", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.throwOnGetInputStream = true;
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        this.fileRequestHandler.getResponse(request);
    }

}
