package com.bivgroup.broker.mq.common.log.layouts;

/**
 * Created by bush on 18.07.2016.
 * Layout for old log4j until ver 2
 */

import com.bivgroup.broker.mq.common.log.Constants;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class ElasticSearchJSONLayout extends JSONLayout {


    private String index = "json-index";
    private String type = "json";

    @Override
    public String format(LoggingEvent loggingEvent) {
        StringBuilder sb = new StringBuilder();
        JSONObject action = new JSONObject();
        JSONObject source = new JSONObject();
        try {
            JSONObject actionContent = new JSONObject();
            actionContent.put(Constants._INDEX, this.index);
            actionContent.put(Constants._TYPE, this.type);
            action.put("index", actionContent);
            JSONObject sourceContent = new JSONObject();
            //== write basic fields
            writeBasic(sourceContent, loggingEvent);
            //== write throwable fields
            writeThrowable(sourceContent, loggingEvent);
            source.put(this.type, sourceContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sb.append(action.toString());
        sb.append("\n");
        sb.append(source.toString());
        sb.append("\n");
        return sb.toString();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }

}
