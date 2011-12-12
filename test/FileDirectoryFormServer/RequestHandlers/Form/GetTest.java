package FileDirectoryFormServer.RequestHandlers.Form;

import HttpServer.Request;
import HttpServer.Responses.OK;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class GetTest {

    Get formGet;

    @Before
    public void setUp() throws Exception {
        this.formGet = new Get();
    }

    @After
    public void tearDown() throws Exception {
        this.formGet = null;
    }

    @Test
    public void respondsToGetWithFormUrl() throws Exception {
        Request request = new Request("GET", "/form", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertTrue(this.formGet.canRespond(request));
    }

    @Test
    public void doesNotRespondToGetWithoutFormUrl() throws Exception {
        Request request = new Request("GET", "/otherurl", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertFalse(this.formGet.canRespond(request));
    }

    @Test
    public void doesNotRespondToPostWithFormUrl() throws Exception {
        Request request = new Request("POST", "/form", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertFalse(this.formGet.canRespond(request));
    }

    @Test
    public void doesNotRespondToPostWithoutFormUrl() throws Exception {
        Request request = new Request("POST", "/otherurl", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertFalse(this.formGet.canRespond(request));
    }

    @Test
    public void getsHtmlForField() throws Exception {
        assertEquals("Field1: <input type=\"text\" name=\"Field1\" />", this.formGet.getHtmlForField("Field1"));
    }

    @Test
    public void getsHtmlForFields() throws Exception {
        assertEquals("Field1: <input type=\"text\" name=\"Field1\" /><br />Field2: <input type=\"text\" name=\"Field2\" /><br />", this.formGet.getHtmlForFields(Arrays.asList("Field1", "Field2")));
    }

    @Test
    public void getsForm() throws Exception {
        assertEquals("<form name=\"input\" action=\"/form\" method=\"POST\"><input type=\"submit\" value=\"Submit\" /></form>", this.formGet.getForm(new ArrayList<String>()));
    }

    @Test
    public void returnsOK() throws Exception {
        Request request = new Request("GET", "/form", "HTTP/1.1", new HashMap<String, String>(), new HashMap<String, Object>());
        assertEquals(OK.class, this.formGet.getResponse(request).getClass());
    }
}
