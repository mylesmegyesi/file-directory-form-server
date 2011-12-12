package FileDirectoryFormServer.Mocks;

import HttpServer.HttpServer;

import java.io.IOException;

/**
 * Author: Myles Megyesi
 */
public class HttpServerMock extends HttpServer {

    public int startCalledCount = 0;
    public boolean throwOnStart = false;

    public HttpServerMock(int port) {
        super(port);
    }

    @Override
    public void start() throws IOException {
        this.startCalledCount++;
        if (this.throwOnStart) {
            throw new IOException();
        }
    }
}
