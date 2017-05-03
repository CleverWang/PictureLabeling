package com.wangcong.picturelabeling.Utils;

/**
 * Created by 13307 on 2017/3/22.
 */

public class GlobalFlags {
    static private String userID = new String();
    static private boolean isNeedtoRefresh = false;
    static private String IpAddress = "http://114.115.130.89:80/ssh_pic/";
    static private boolean isLoggedIn = false;
    static private String sessionId = "";

    public static boolean isNeedtoRefresh() {
        return isNeedtoRefresh;
    }

    public static void setIsNeedtoRefresh(boolean isNeedtoRefresh) {
        GlobalFlags.isNeedtoRefresh = isNeedtoRefresh;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        GlobalFlags.sessionId = sessionId;
    }

    public static String getIpAddress() {
        return IpAddress;
    }

    public static void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        GlobalFlags.userID = userID;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        GlobalFlags.isLoggedIn = isLoggedIn;
    }
}
