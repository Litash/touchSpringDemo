package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Set;

@Service
public class TimeService {

    public String getCurrentDateTime(){
        return LocalDateTime.now().format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.UK));
    }

    public Set<String> getAllTimeZone(){
        return ZoneId.getAvailableZoneIds();
    }

    public String getZonedDateTime(String zoneStr){
        ZoneId zoneId = ZoneId.of(zoneStr);
        return ZonedDateTime.now(zoneId).format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.UK));
    }
}
