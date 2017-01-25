package javaproject.epitech.eu;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class HttpError {
    public String error;

    HttpError(HttpServletResponse r, String error, int code) {
        this.error = error;
        r.setStatus(code);
        try {
            r.flushBuffer();
        } catch (IOException e) {}
    }
}