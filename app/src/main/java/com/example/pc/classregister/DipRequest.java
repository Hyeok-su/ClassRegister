package com.example.pc.classregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 2017-03-13.
 */

public class DipRequest extends StringRequest{

    final static private String URL = ""; // CourseDip.php
    private Map<String, String> parameters;

    public DipRequest(String userID, String courseID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
