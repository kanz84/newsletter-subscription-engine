package com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.Locale;


public class TestUtility {
    public static  <T> T readJson(MvcResult mvcResult, Class<T> clazz) throws java.io.IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }

    public static String toDateString(Date from) {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setIso(DateTimeFormat.ISO.DATE_TIME);
        return dateFormatter.print(from, Locale.getDefault());
    }

}
