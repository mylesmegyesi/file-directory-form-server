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
import java.util.Arrays;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Get implements RequestHandler {
    public boolean canRespond(Request request) {
        return request.action.equals("GET") && request.url.equals("/form");
    }

    public Response getResponse(Request request) throws ResponseException {
        String html = this.getHtml();
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

    private String getHtml() {
        List<String> fields = Arrays.asList("Field1", "Field2", "Field3");
        return getForm(fields);
    }

    public String getForm(List<String> fields) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<form name=\"input\" action=\"/form\" method=\"POST\">"));
        builder.append(this.getHtmlForFields(fields));
        builder.append("<input type=\"submit\" value=\"Submit\" />");
        builder.append("</form>");
        return builder.toString();
    }

    public String getHtmlForFields(List<String> fields) {
        StringBuilder builder = new StringBuilder();
        for (String field : fields) {
            builder.append(this.getHtmlForField(field));
            builder.append("<br />");
        }
        return builder.toString();
    }

    public String getHtmlForField(String fieldName) {
        return String.format("%s: <input type=\"text\" name=\"%s\" />", fieldName, fieldName);
    }
}
