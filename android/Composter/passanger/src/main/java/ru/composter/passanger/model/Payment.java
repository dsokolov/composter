package ru.composter.passanger.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.composter.passanger.R;

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

    public static ArrayAdapter getAdapter(Context context, List<Payment> content) {
        return new Adapter(context, content);
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

    public static class Adapter extends ArrayAdapter<Payment> {
        private SimpleDateFormat SDF = new SimpleDateFormat("dd MMM hh:mm");

        public Adapter(Context context, List<Payment> objects) {
            super(context, -1, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.listitem_payment, null);
            }
            Payment payment = getItem(position);
            ((TextView)convertView.findViewById(R.id.routeNumber)).setText(payment.getRouteNumber());
            ((TextView)convertView.findViewById(R.id.timestamp)).setText(SDF.format(payment.getTimestamp()));
            ((TextView)convertView.findViewById(R.id.routeNumber)).setText("+" + payment.getPrice() + "Р");
            return convertView;
        }
    }
}
