import com.sun.xml.internal.rngom.parse.host.Base;

import java.util.Base64;

public class SessionManager {
        String token = "";

        public static SessionManager getInstance() {
            if (null == instance) {
                instance = new SessionManager();
            }
            return instance;
        }

        private SessionManager() {
        }

        public void saveToken(String username, String password) {
            byte[] encodedBytes = Base64.getEncoder().encode((username + ":" + password).getBytes());
            this.token = new String(encodedBytes);
            CacheManager.saveContent(CacheManager.CREDENTIALS_FILENAME, this.token);
        }

        public void loadToken() {
            this.token = CacheManager.loadContent(CacheManager.CREDENTIALS_FILENAME);
        }

        public static Boolean isLoggedIn() {
            SessionManager.getInstance().loadToken();
            return !SessionManager.getInstance().token.isEmpty();
        }

        public static String getToken() {
            SessionManager.getInstance().loadToken();
            return SessionManager.getInstance().token;
        }

        private static SessionManager instance;
}
