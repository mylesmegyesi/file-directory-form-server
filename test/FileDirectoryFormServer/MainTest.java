package FileDirectoryFormServer;

import FileDirectoryFormServer.Mocks.HttpServerMock;
import HttpServer.RequestHandler;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Author: Myles Megyesi
 */
public class MainTest {

    @Test
    public void addsAllRequestHandlers() throws Exception {
        List<RequestHandler> requestHandlers = Main.setupRequestHandlers();
        assertEquals(4, requestHandlers.size());
    }

    @Test
    public void startServerCallsStart() throws Exception {
        HttpServerMock serverMock = new HttpServerMock(0);
        Main.startServer(serverMock);
        assertEquals(1, serverMock.startCalledCount);
    }

    @Test
    public void catchesIOException() throws Exception {
        HttpServerMock serverMock = new HttpServerMock(0);
        serverMock.throwOnStart = true;
        try {
            Main.startServer(serverMock);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void forLessThanFourArgsLeavesTheDefaults() throws Exception {
        Main.parseArgs(new String[]{"one"});
        assertEquals(".", Main.directory);
        assertEquals(8080, Main.port);
    }

    @Test
    public void forFourUses2and4() throws Exception {
        Main.parseArgs(new String[]{"one", "8090", "three", "someotherdir"});
        assertEquals("someotherdir", Main.directory);
        assertEquals(8090, Main.port);
    }
}
