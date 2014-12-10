package be.chiroelzestraat.webservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setDateFormat(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'ZZZ (z)"));
    }
}