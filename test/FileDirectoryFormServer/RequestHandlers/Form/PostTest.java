package FileDirectoryFormServer.RequestHandlers.Form;

import HttpServer.Request;
import HttpServer.Responses.OK;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class PostTest {
    Post formPost;

    @Before
    public void setUp() throws Exception {
        this.formPost = new Post();
    }

    @After
    public void tearDown() throws Exception {
        this.formPost = null;
    }

    @Test
    public void respondsToGetWithFormUrl() throws Exception {
        Request request = new Request("GET", "/form", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertFalse(this.formPost.canRespond(request));
    }

    @Test
    public void doesNotRespondToGetWithoutFormUrl() throws Exception {
        Request request = new Request("GET", "/otherurl", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertFalse(this.formPost.canRespond(request));
    }

    @Test
    public void doesNotRespondToPostWithFormUrl() throws Exception {
        Request request = new Request("POST", "/form", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertTrue(this.formPost.canRespond(request));
    }

    @Test
    public void doesNotRespondToPostWithoutFormUrl() throws Exception {
        Request request = new Request("POST", "/otherurl", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertFalse(this.formPost.canRespond(request));
    }

    @Test
    public void getsTD() throws Exception {
        assertEquals("<td>Key</td>", this.formPost.getTd("Key"));
    }

    @Test
    public void getsRow() throws Exception {
        assertEquals("<tr><td>Key</td><td>Value</td></tr>", this.formPost.getRow("Key", "Value"));
    }

    @Test
    public void getsATableWithHeaderRow() throws Exception {
        assertEquals("<table><tr><td>Parameter</td><td>Value</td></tr></table>", this.formPost.getTable(new HashMap<String, Object>()));
    }

    @Test
    public void returnsOK() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("thing", "foo");
        Request request = new Request("GET", "/form", "HTTP/1.1", new HashMap<String, String>(), params);
        assertEquals(OK.class, this.formPost.getResponse(request).getClass());
    }
}
