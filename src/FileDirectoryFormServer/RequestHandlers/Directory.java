package FileDirectoryFormServer.RequestHandlers;

import HttpServer.Exceptions.ResponseException;
import HttpServer.Headers.ConnectionClose;
import HttpServer.Headers.DateHeader;
import HttpServer.Request;
import HttpServer.RequestHandler;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Responses.OK;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Directory implements RequestHandler {

    public Directory(String directoryToServe, FileDirectoryFormServer.Utility.FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    public boolean canRespond(Request request) {
        return request.action.equals("GET") && fileInfo.directoryExists(this.directoryToServe, request.url);
    }

    public Response getResponse(Request request) throws ResponseException {
        String html = this.getDirectoryHtml(request.url);
        List<ResponseHeader> responseHeaders = setupHeaders(String.valueOf(html.length()));
        return new OK(responseHeaders, new ByteArrayInputStream(html.getBytes()));
    }

    private List<ResponseHeader> setupHeaders(String contentLength) {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader());
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        responseHeaders.add(new ConnectionClose());
        responseHeaders.add(new ResponseHeader("Content-Length", contentLength));
        return responseHeaders;
    }

    public String getDirectoryHtml(String directory) {
        List<String> rows = this.getDirectoryRows(directory);
        return this.createTable(rows);
    }

    public List<String> getDirectoryRows(String directory) {
        List<String> rows = new ArrayList<String>();
        for (String entry : this.fileInfo.getEntries(this.directoryToServe, directory)) {
            rows.add(this.getRow(directory, entry));
        }
        return rows;
    }

    public String getRow(String parent, String entry) {
        StringBuilder builder = new StringBuilder();
        builder.append("<tr>");
        builder.append("<td>");
        builder.append(this.getLink(parent, entry));
        builder.append("</td>");
        builder.append("</tr>");
        return builder.toString();
    }

    public String getLink(String parent, String entry) {
        return String.format("<a href=\"%s\">%s</a>", this.fileInfo.getRelativePath(parent, entry), entry);
    }

    public String createTable(List<String> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        for (String row : rows) {
            builder.append(row);
        }
        builder.append("</table>");
        return builder.toString();
    }

    private FileDirectoryFormServer.Utility.FileInfo fileInfo;
    private String directoryToServe;
}
