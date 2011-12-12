package FileDirectoryFormServer.RequestHandlers;

import FileDirectoryFormServer.Utility.FileInfo;
import HttpServer.Exceptions.ResponseException;
import HttpServer.Headers.ConnectionClose;
import HttpServer.Headers.DateHeader;
import HttpServer.Request;
import HttpServer.RequestHandler;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Responses.OK;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class File implements RequestHandler {

    public File(String directoryToServe, FileDirectoryFormServer.Utility.FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    public boolean canRespond(Request request) {
        if (!request.action.equals("GET")) {
            return false;
        }
        return fileInfo.fileExists(this.directoryToServe, request.url);
    }

    public Response getResponse(Request request) throws ResponseException {
        java.io.File fileToServe = this.fileInfo.getFile(this.directoryToServe, request.url);
        List<ResponseHeader> responseHeaders = setupHeaders(fileToServe);
        InputStream inputStream = getInputStream(fileToServe);
        return new OK(responseHeaders, inputStream);
    }

    private List<ResponseHeader> setupHeaders(java.io.File fileToServe) {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader());
        responseHeaders.add(new ResponseHeader("Content-Type", this.fileInfo.getContentType(fileToServe.getName())));
        responseHeaders.add(new ResponseHeader("Last-Modified", new HttpServer.Utility.Date().formattedDate(new Date(fileToServe.lastModified()))));
        responseHeaders.add(new ResponseHeader("Content-Length", String.valueOf(fileToServe.length())));
        responseHeaders.add(new ConnectionClose());
        return responseHeaders;
    }

    private InputStream getInputStream(java.io.File fileToServe) throws ResponseException {
        try {
            return this.fileInfo.getInputStream(fileToServe);
        } catch (FileNotFoundException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    private FileInfo fileInfo;
    private String directoryToServe;
}
