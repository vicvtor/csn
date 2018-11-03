package com.brichenko.csn.component;

import com.brichenko.csn.component.parser.AbstractParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Планировщик.
 */
@Component
public class Scheduller {

    @Autowired
    Map<String, AbstractParser> parsers;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void parseLastNews(){
        parsers.forEach((k,v) -> v.parseNews());
    }

}
