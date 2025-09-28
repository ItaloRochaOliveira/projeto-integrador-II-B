package models.APIConection;

import java.util.Map;

public class Request {
    public String path;
    public String method;
    public String body;
    public Map<String, String> headers;
}