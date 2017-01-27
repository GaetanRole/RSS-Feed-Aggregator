import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.security.pkcs.ParsingException;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HTTPRequest {
    enum HTTPRequestType {
        GET("GET"),
        POST("POST"),
        DELETE("DELETE");

        private final String name;

        private HTTPRequestType(String s) {
            name = s;
        }
    }

    public interface HTTPRequestTask {
        public void onPostExecute(HTTPResponse res);
    }

    private HttpURLConnection connection;
    private HTTPRequestType type;
    private String endPoint;
    private Map<String,String> params;

    public HTTPRequest(HTTPRequestType type, String endPoint, Map<String,String> params) {
        this.type = type;
        this.endPoint = endPoint;
        this.params = params;
    }

    public void addHeader(String key, String value) {
        this.connection.setRequestProperty(key, value);
    }

    public void execute(HTTPRequestTask task) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HTTPRequest request = HTTPRequest.this;

                String str = (new StringBuilder()).append("http://149.202.169.91").append(endPoint).toString();
                JsonObject data = null;
                String getData = null;
                InputStream is = null, es = null;
                OutputStream os = null;

                try {
                    if (params != null) {
                        StringBuilder postData = new StringBuilder();
                        data = new JsonObject();
                        for (Map.Entry<String, String> param : params.entrySet()) {
                            data.add(param.getKey(), param.getValue());
                            if (postData.length() != 0) postData.append('&');
                            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                            postData.append('=');
                            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                        }

                        getData = postData.toString();
                    }

                    if (type != HTTPRequestType.GET) {
                        URL url = new URL(str);
                        request.connection = (HttpURLConnection)url.openConnection();
                        if (data != null) {
                            request.connection.setDoOutput(true);
                            request.connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            request.connection.setRequestProperty("Authorization", "Basic " + SessionManager.getToken());
                            //this.connection.getOutputStream().write(getData.getBytes("UTF-8"));
                            os = request.connection.getOutputStream();
                            DataOutputStream wr = new DataOutputStream(os);
                            wr.writeChars(data.toString());
                            wr.flush();
                            wr.close();
                        } else {
                            request.connection.setRequestMethod(type.name());
                            request.connection.setRequestProperty("Authorization", "Basic " + SessionManager.getToken());
                        }
                    } else {
                        if (getData != null) str += ("?" + getData);
                        URL url = new URL(str);
                        request.connection = (HttpURLConnection)url.openConnection();
                        request.connection.setRequestMethod(type.name());
                        request.connection.setRequestProperty("Authorization", "Basic " + SessionManager.getToken());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // RESPONSE
                int statusCode = -1;
                JsonValue obj = null;
                Error err = null;
                Boolean called = false;

                try {
                    statusCode = request.connection.getResponseCode();
                    if (statusCode < 200 || statusCode >= 400) {
                        err = new Error();
                    }

                    if (statusCode == 403 && !(AppWindow.getInstance().getContentPane().getComponent(0) instanceof LoginPanel)) {
                        called = true;
                        JOptionPane.showMessageDialog(null, "Your session has expired. Please login again.", "Warning", JOptionPane.WARNING_MESSAGE);
                        CacheManager.saveContent(CacheManager.CREDENTIALS_FILENAME, "");
                        AppWindow.getInstance().changePanel(new LoginPanel());
                    }

                    is = request.connection.getInputStream();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(is));
                    String inputLine;
                    StringBuffer bf = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        bf.append(inputLine);
                    }

                    in.close();
                    obj = Json.parse(bf.toString());
                } catch (ParsingException e) {
                    
                } catch (IOException e) {
                    err = new Error(e.getMessage(), e.getCause());
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                        }
                    }

                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                        }
                    }

                    System.out.println("Disconnect");
                    request.connection.disconnect();
                }

                if (!called) {
                    HTTPResponse res = new HTTPResponse(statusCode, obj, err);
                    task.onPostExecute(res);
                }
            }
        });

        thread.start();
    }

    // Static methods
    public static HTTPRequest GET(String endPoint, Map<String,String> params) {
        return new HTTPRequest(HTTPRequestType.GET, endPoint, params);
    }

    public static HTTPRequest POST(String endPoint, Map<String,String> params) {
        return new HTTPRequest(HTTPRequestType.POST, endPoint, params);
    }

    public static HTTPRequest DELETE(String endPoint, Map<String,String> params) {
        return new HTTPRequest(HTTPRequestType.DELETE, endPoint, params);
    }
}
