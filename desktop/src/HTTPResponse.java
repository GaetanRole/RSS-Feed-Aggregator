import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class HTTPResponse {
    private int statusCode;
    private JsonValue body;
    private Error error;

    public HTTPResponse(int statusCode, JsonValue body, Error error) {
        this.statusCode = statusCode;
        this.body = body;
        this.error = error;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public JsonValue getBody() {
        return this.body;
    }

    public Error getError() {
        return this.error;
    }
}
