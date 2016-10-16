package ru.composter.passanger.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Elizarov (elizarov1988@gmail.com)
 *         on 16.10.16 10:55.
 */

public class Payment {
    public static List<Payment> from(JSONArray array) {
        List<Payment>  result = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); ++i) {
                try {
                    result.add(new Payment(array.getJSONObject(i)));
                } catch (JSONException ignored) {
                }
            }
        }
        return result;
    }
    private String id;
    private String routeNumber;
    private String price;
    private String timestamp;
    private String type;
    private Payer payer;

    public Payment(JSONObject json) {
        if (json != null) {
            id = json.optString("id");
            routeNumber = json.optString("routeNumber");
            price = json.optString("price");
            timestamp = json.optString("timestamp");
            type = json.optString("type");
            JSONObject payerJson = json.optJSONObject("payer");
            if (payerJson != null) {
                payer = new Payer(payerJson);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public String getPrice() {
        return price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public Payer getPayer() {
        return payer;
    }

    public class Adapter extends ArrayAdapter<Payment> {

        public Adapter(Context context, List<Payment> objects) {
            super(context, -1, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }
}
