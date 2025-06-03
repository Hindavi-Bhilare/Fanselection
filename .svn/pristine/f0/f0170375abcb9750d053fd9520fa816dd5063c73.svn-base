package com.velotech.fanselection.velotech;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.octomix.josson.Josson;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import org.json.JSONException;
import org.json.JSONObject;

public class UnitTest {

    public static void main(String[] args) throws Exception {
        
       /* // in()
        evaluate("56.in(12,34,56)");
        evaluate("'56'.in(12,34,56)");
        evaluate("'A'.in(json('[\"a\",\"b\",\"c\"]'))");
        // inIgnoreCase()
        evaluate("'A'.inIgnoreCase('a','b','c')");
        evaluate("'a '.inIgnoreCase('a','b','c')");
        // notIn()
*/        //evaluate("56.notIn(12,34,56)");
       evaluate("'56'.notIn(12,34,56)");
        //evaluate("'A'.notIn(json('[\"a\",\"b\",\"c\"]'))");
    }
    
    public static void evaluate(String path) throws JsonProcessingException, JSONException{
    	JSONObject jo = new JSONObject();
		jo.put("stg", 2);
		
		Josson josson = Josson.fromJsonString(jo.toString());
    	  JsonNode node = josson.getNode(path);
    	  System.out.println(node);
    }
    

}