package com.brichenko.csn.component.parser.impl;

import com.brichenko.csn.component.parser.AbstractParser;
import com.brichenko.csn.entity.enums.NewsCategory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Парсер новостей с портала www.cybersport.ru.
 */
@Component
public class CybersportParser extends AbstractParser {

    @Override
    public String getNewsUrl() {
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        StringBuilder url = new StringBuilder(getHostName());
        url.append("/news/from/").append(LocalDateTime.now().minusDays(7).format(formatter));
        url.append("/to/").append(LocalDateTime.now().format(formatter));
        return url.toString();*/

        return getHostName();
    }

    @Override
    public String getHostName() {
        return "https://www.cybersport.ru" ;
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
    public LocalDateTime parsePublished(Element e) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date = resolveDateElement(e).attr("datetime").split("T")[0];
        String time = e.selectFirst(".community__title").child(0).text();

        return LocalDateTime.parse(date + ' ' + time, formatter);
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
