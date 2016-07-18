package com.bivgroup.broker.mq.common.log.layouts;

/**
 * Created by bush on 18.07.2016.
 * Layout for old log4j until ver 2
 */

import com.bivgroup.broker.mq.common.log.Constants;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class JSONLayout extends Layout {
    @Override
    public String format(LoggingEvent loggingEvent) {
        JSONObject root = new JSONObject();
        try {
            //== write basic fields
            writeBasic(root, loggingEvent);
            //== write throwable fields
            writeThrowable(root, loggingEvent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    protected void writeThrowable(JSONObject json, LoggingEvent event) throws JSONException {
        ThrowableInformation ti = event.getThrowableInformation();
        if (ti != null) {
            Throwable t = ti.getThrowable();
            JSONObject throwable = new JSONObject();
            throwable.put(Constants.MESSAGE, t.getMessage());
            throwable.put(Constants.CLASS_NAME, t.getClass().getCanonicalName());
            List<JSONObject> traceObjects = new ArrayList<JSONObject>();
            for (StackTraceElement ste : t.getStackTrace()) {
                JSONObject element = new JSONObject();
                element.put(Constants.CLASS, ste.getClassName());
                element.put(Constants.METHOD_NAME, ste.getMethodName());
                element.put(Constants.LINE, ste.getLineNumber());
                element.put(Constants.FILE_NAME, ste.getFileName());
                traceObjects.add(element);
            }
            json.put(Constants.STACK_TRACE, traceObjects);
            json.put(Constants.THROWABLE, throwable);
        }
    }

    protected void writeBasic(JSONObject json, LoggingEvent event) throws JSONException {
        json.put(Constants.THREAD_NAME, event.getThreadName());
        json.put(Constants.LEVEL, event.getLevel().toString());
        json.put(Constants.TIMESTEMP, event.getTimeStamp());
        json.put(Constants.MESSAGE, event.getMessage());
        json.put(Constants.LOGGER, event.getLoggerName());
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {
    }
}

