package ru.composter.passanger.model;

import org.json.JSONObject;

/**
 * @author Sergey Elizarov (elizarov1988@gmail.com)
 *         on 16.10.16 10:57.
 */

public class Payer {
    private String id;
    private String name;

    public Payer(JSONObject json) {
        if (json != null) {
            id = json.optString("id");
            name = json.optString("name");
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
