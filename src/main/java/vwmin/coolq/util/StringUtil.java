package vwmin.coolq.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String getDigest(String src){
        return src.length() > 200
                ? src.substring(0, 201)
                : src;
    }
    public static String matchUrl(String message){
        String urlRegex = "https://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(urlRegex);
        Matcher m = p.matcher(message);
        String url = "";
        if(m.find()) {
            url = m.group();
        }

        return url;
    }

    public static String[] append(String[] args, String newArg){
        String[] res = new String[args.length+1];
        System.arraycopy(args, 0, res, 0, args.length);
        res[args.length] = newArg;
        return res  ;
    }
}
