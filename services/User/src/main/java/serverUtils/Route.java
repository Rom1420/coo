package serverUtils;

public class Route {
    private final String method;
    private final String path;
    private final RouteHandler handler;

    public Route(String method, String path, RouteHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public RouteHandler getHandler() {
        return handler;
    }
}

