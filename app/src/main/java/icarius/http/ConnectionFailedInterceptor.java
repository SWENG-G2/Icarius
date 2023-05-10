package icarius.http;

import java.awt.Color;
import java.io.IOException;
import java.net.ConnectException;

import icarius.gui.Gui;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ConnectionFailedInterceptor implements Interceptor {
    public Gui gui;

    public ConnectionFailedInterceptor(Gui gui) {
        super();
        this.gui = gui;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            return chain.proceed(request);
        } catch (ConnectException ce) {
            // Show failed connection message
            if (gui.footerPanel != null) {
                gui.footerPanel.setNotification("Server is unreachable, please try again later.", Color.RED);
            }

            // Return empty response
            return new Response.Builder()
            .code(404)
            .body(ResponseBody.create("", null))
            .message("Server Connection Failed")
            .protocol(Protocol.HTTP_2)
            .request(chain.request())
            .build();
        }
    }
}
