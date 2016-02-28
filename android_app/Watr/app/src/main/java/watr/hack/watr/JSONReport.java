package watr.hack.watr;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by peter on 2/27/16.
 */
public class JSONReport {
    public double longitude;
    public double latitude;

    public JSONReport(JsonObject json) {
        try {
            JsonObject pos = json.getAsJsonObject("position");
            JsonArray coords = pos.getAsJsonArray("coordinates");
            latitude = coords.get(0).getAsDouble();
            longitude = coords.get(1).getAsDouble();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
