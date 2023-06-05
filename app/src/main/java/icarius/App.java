package icarius;

import icarius.gui.Gui;
import okhttp3.OkHttpClient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import icarius.auth.UserClient;
import icarius.entities.Database;

public class App {
    public static final String BASE_URL = "https://penelope.barillari.me:8080";
    public static UserClient userClient;
    public static Database db;
    public Gui gui;

    // Variables used when hosting locally
    public static final String PENELOPE_STORAGE = "/Desktop/sweng/penelope_storage";

    public static void main(String[] args) {
        userClient = new UserClient(new OkHttpClient());
        Gui gui = new Gui();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
          // Create a trust manager that does not validate certificate chains
          final TrustManager[] trustAllCerts = new TrustManager[] {
              new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }
      
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }
      
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                  return new java.security.cert.X509Certificate[]{};
                }
              }
          };
      
          // Install the all-trusting trust manager
          final SSLContext sslContext = SSLContext.getInstance("SSL");
          sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
          // Create an ssl socket factory with our all-trusting manager
          final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      
          OkHttpClient.Builder builder = new OkHttpClient.Builder();
          builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
          builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
              return true;
            }
          });
      
          OkHttpClient okHttpClient = builder.build();
          return okHttpClient;
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
}
