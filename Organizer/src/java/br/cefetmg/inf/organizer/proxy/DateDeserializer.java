package br.cefetmg.inf.organizer.proxy;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date>{
    
    private static final String[] DATE_FORMATS = new String[]{        
        "yyyy-MM-dd"        
    };

    @Override
    public Date deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        for (String format : DATE_FORMATS) {
        try {
            return new SimpleDateFormat(format, Locale.ROOT).parse(je.getAsString());
        } catch (ParseException e) {
        }
    }
    throw new JsonParseException("Unparseable date: \"" + je.getAsString()
            + "\". Supported format: " + Arrays.toString(DATE_FORMATS));
    }
    
    // http://www.guj.com.br/t/resolvido-problemas-com-gson-do-google-unparseable-date-abr-19-1991/361577
    
}
