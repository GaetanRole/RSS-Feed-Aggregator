import java.io.*;

public class CacheManager {
    static String TAGS_CACHE_FILENAME = "tags.cache.json";
    static String FEED_CONTENT_CACHE_FILENAME = "feed-content.cache.json";
    static String FEEDS_LIST_CACHE_FILENAME = "feeds-list.cache.json";
    static String CREDENTIALS_FILENAME = "cred.cache.txt";

    static String loadContent(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            sb.append(line);

            /*while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }*/

            String everything = sb.toString();
            br.close();
            return everything;
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    static void saveContent(String file, String content) {
        try {
            PrintWriter out = new PrintWriter( file);
            out.println(content);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("CANNOT WRITE");
        }
    }
}
