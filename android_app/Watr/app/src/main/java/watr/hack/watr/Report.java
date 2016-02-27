package watr.hack.watr;

import android.util.Log;

/**
 * Created by peter on 2/26/16.
 */
public class Report {

    int quality;
    String zip;
    String color;

    public String getParameterString() {
        String str = "";
        str += "quality=" + quality + "&";
        str += "color=" + color + "&";
        str += "zip=" + zip;
        return str;
    }
}
