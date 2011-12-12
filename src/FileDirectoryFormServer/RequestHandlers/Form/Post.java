package FileDirectoryFormServer.RequestHandlers.Form;

import HttpServer.Exceptions.ResponseException;
import HttpServer.Headers.DateHeader;
import HttpServer.Request;
import HttpServer.RequestHandler;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Responses.OK;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class Post implements RequestHandler {
    public boolean canRespond(Request request) {
        return request.action.equals("POST") && request.url.equals("/form");
    }

    public Response getResponse(Request request) throws ResponseException {
        String html = this.getTable(request.parameters);
        List<ResponseHeader> responseHeaders = setupHeaders(String.valueOf(html.length()));
        return new OK(responseHeaders, new ByteArrayInputStream(html.getBytes()));
    }

    private List<ResponseHeader> setupHeaders(String contentLength) {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader());
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        responseHeaders.add(new ResponseHeader("Content-Length", contentLength));
        return responseHeaders;
    }

    public String getTable(Map<String, Object> parameters) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        builder.append(this.getRow("Parameter", "Value"));
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            builder.append(this.getRow(param.getKey(), (String) param.getValue()));
        }
        builder.append("</table>");
        return builder.toString();
    }

    public String getRow(String key, String value) {
        return "<tr>" + getTd(key) + getTd(value) + "</tr>";
    }

    public String getTd(String value) {
        return "<td>" + value + "</td>";
    }


}
