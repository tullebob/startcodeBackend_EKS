package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class HttpUtils {

    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        //con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");
        con.setRequestProperty("Content-Type", "application/json");
        
        if (url.toString().contains("api.digitalocean")) {
            con.setRequestProperty("Authorization","Bearer "+ Keys.digitalOceanBearer);
        }
        
        Scanner scan = new Scanner(con.getInputStream(), "UTF-8");
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }
}
