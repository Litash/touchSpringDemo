package com.example.demo.api;

import com.example.demo.service.TimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("api/v1/time")
@RestController
public class TimeGetterController {

    private final TimeService timeService;

    public TimeGetterController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping(path = "/now")
    public String getCurrentLocalDateTime(){
        return timeService.getCurrentDateTime();
    }

    @GetMapping(path = "/zones")
    public Set<String> getTimeZones(){
        return timeService.getAllTimeZone();
    }

    @GetMapping(path = "/zone")
    public String getZonedDateTime(@RequestParam(name = "zoneid") String zoneStr){
        return timeService.getZonedDateTime(zoneStr);
    }
}
