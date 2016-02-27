package watr.hack.watr;

import android.util.Log;

/**
 * Created by peter on 2/26/16.
 */
public class Report {

    int quality;
    String town;
    String state;

    public String getParameterString() {
        String str = "";
        str += "quality=" + quality + "&";
        str += "town=" + town + "&";
        str += "state=" + state;
        return str;
    }
}
