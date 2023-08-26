package de.beres.search.operations;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Component
public class DateOperations {
    private Date date;
    private int year;
    private int month;
    private int day;
    private String yearAsFormattedString;
    private String yearMonthDayAsFormattedString;
    private String hour;
    private String minute;
    private String second;
    @SneakyThrows(ParseException.class)
    void convertFromMetadata(String dateMetaFormat){
        DateFormat format;
        if(! dateMetaFormat.contains("TZ"))
            format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.ENGLISH);
        else
            format = new SimpleDateFormat("yyyy:MM:dd kk:mm:ss", Locale.ENGLISH);
            date = format.parse(dateMetaFormat);
            convertDateTo2AtomicElements();
            convertAtomicElements2FormattedResulst();
    }
    void convertDateTo2AtomicElements(){
        DateFormat df = DateFormat.getDateInstance();
        df.format(date);
        year = df.getCalendar().get(Calendar.YEAR);
        month = df.getCalendar().get(Calendar.MONTH) + 1;
        day = df.getCalendar().get(Calendar.DAY_OF_MONTH) + 1;
    }
    void convertAtomicElements2FormattedResulst(){
        yearAsFormattedString = new SimpleDateFormat("yyyy").format(date);
        yearMonthDayAsFormattedString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        log.info(yearAsFormattedString);
        log.info(yearMonthDayAsFormattedString);
    }
}
