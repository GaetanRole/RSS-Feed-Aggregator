package olivier.sturbois.rss_project;

import android.util.Base64;

public class APIRoutes {

    private static String IpAddress = "http://149.202.169.91";
    private static String FeedsRoute = "/feeds";
    private static String TagsRoute = "/tags";
    private static String AddFeedRoute = "/feeds";

    public static String GetFeedsRoute() {
        return IpAddress.concat(FeedsRoute);
    }

    public static String GetAddFeedRoute() {
        return IpAddress.concat(AddFeedRoute);
    }

    public static String GetFeedByIdRoute(String id) {
        return IpAddress.concat(FeedsRoute).concat("/").concat(id);
    }

    public static String GetTagsRoute() {
        return IpAddress.concat(TagsRoute);
    }

    public static String GetAuthorizationHeader(User u) {
        return ("basic ".concat(Base64.encodeToString((u.getLogin() + ":" + u.getPassword()).getBytes(), Base64.DEFAULT)).replace("\n", ""));
    }
}
