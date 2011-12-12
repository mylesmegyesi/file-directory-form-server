package FileDirectoryFormServer;

import FileDirectoryFormServer.RequestHandlers.Directory;
import FileDirectoryFormServer.RequestHandlers.File;
import FileDirectoryFormServer.RequestHandlers.Form.Get;
import FileDirectoryFormServer.RequestHandlers.Form.Post;
import FileDirectoryFormServer.Utility.FileInfo;
import HttpServer.HttpServer;
import HttpServer.RequestHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Main {

    static int port = 8080;
    static String directory = ".";
    static FileInfo fileInfo = new FileInfo();

    public static void main(String[] args) {
        parseArgs(args);
        startServer(new HttpServer(port, setupRequestHandlers()));
    }

    public static void parseArgs(String[] args) {
        if (args.length == 4) {
            port = Integer.parseInt(args[1]);
            directory = args[3];
        }
    }

    public static List<RequestHandler> setupRequestHandlers() {
        List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();
        requestHandlers.add(new Post());
        requestHandlers.add(new Get());
        requestHandlers.add(new Directory(directory, fileInfo));
        requestHandlers.add(new File(directory, fileInfo));
        return requestHandlers;
    }

    public static void startServer(HttpServer httpServer) {
        try {
            httpServer.start();
        } catch (IOException e) {
        }
    }
}
