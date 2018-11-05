package com.brichenko.csn.component.parser.impl;

import com.brichenko.csn.component.parser.AbstractParser;
import com.brichenko.csn.entity.enums.NewsCategory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Парсер новостей с портала www.cybersport.ru.
 */
@Component
public class CybersportRuParser extends AbstractParser {

    @Override
    public String getNewsUrl() {
        return getHostUrl();
    }

    @Override
    public String getHostUrl() {
        return "https://www.cybersport.ru";
    }

    @Override
    public String getHostName() {
        return "Cybersport";
    }

    @Override
    public Elements getRawNews(Document doc) {
        return doc.select(".community__item");
    }

    @Override
    public String parseTitle(Element e) {
        return e.selectFirst(".community__title").selectFirst("a").text();
    }

    @Override
    public ZonedDateTime parsePublished(Element e) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date = resolveDateElement(e).attr("datetime").split("T")[0];
        String time = e.selectFirst(".community__title").child(0).text();

        return ZonedDateTime.parse(date + " " + time, formatter.withZone(ZoneOffset.ofHours(3)));
    }

    @Override
    public String parseUrl(Element e) {
        return e.selectFirst(".community__title").child(2).attr("href");
    }

    @Override
    public String parseAuthor(Element e) {
        return null;
    }

    @Override
    public String parseAnnounce(Element e) {
        return null;
    }

    @Override
    public String parseBody(Element e) {
        return null;
    }

    @Override
    public String parseId(Element e) {
        return null;
    }

    @Override
    public String parseImage(Element e) {
        return null;
    }

    @Override
    public NewsCategory parseCategory(Element e) {

        Element cat = e.selectFirst(".icon-games");

        NewsCategory result = NewsCategory.OTHER;
        if (cat != null) {
            if (cat.is(".icon-games--dota2")) {
                result = NewsCategory.DOTA;
            } else if (cat.is(".icon-games--cs-go")) {
                result = NewsCategory.CS;
            } else if (cat.is(".icon-games--overwatch")) {
                result = NewsCategory.OW;
            } else if (cat.is(".icon-games--lol")) {
                result = NewsCategory.LOL;
            }
        }

        return result;
    }

    @Override
    public int parseCommentsCount(Element e) {
        return Integer.valueOf(e.selectFirst(".comment-counter__count").text());
    }

    /**
     * Получает элемент с датой рекурсивно.
     * Т.к. дата публикации новости стоит только над последней новостью, опубликованной в этот день,
     * добираемся до нее рекурсивно.
     *
     * @param e - родительский элемент.
     * @return - элемент, содержащий дату.
     */
    private Element resolveDateElement(Element e){
        Element prevElement = e.previousElementSibling();
        if (prevElement.hasClass("community__date")) {
            return  prevElement.selectFirst("time");
        } else {
            return resolveDateElement(prevElement);
        }
    }
}
