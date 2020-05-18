package org.javaboy.vhr.converter;

import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器 String => Date
 *
 * @ClassName DateConverter
 * @Author 周某
 * @Date 2020-05-17 18:54
 **/
@Component
public class DateConverter implements Converter<String, Date> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
