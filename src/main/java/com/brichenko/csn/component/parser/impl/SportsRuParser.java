package com.brichenko.csn.component.parser.impl;

import com.brichenko.csn.component.parser.AbstractParser;
import com.brichenko.csn.entity.enums.NewsCategory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class SportsRuParser extends AbstractParser {
    @Override
    public String getNewsUrl() {
        return getHostUrl() + "/news/";
    }

    @Override
    public String getHostUrl() {
        return "https://cyber.sports.ru";
    }

    @Override
    public String getHostName() {
        return "Sports";
    }

    @Override
    public Elements getRawNews(Document doc) {
        return doc.select(".active-panel .news p");
    }

    @Override
    public String parseTitle(Element e) {
        return e.select("strong").text();
    }

    @Override
    public ZonedDateTime parsePublished(Element e) {

        formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm").withLocale(new Locale("ru","RU"));

        String date = resolveDateElement(e).text();
        String time = e.selectFirst("span").text();

        StringBuilder stringDate = new StringBuilder(date);

        stringDate.append(" ")
                .append(Year.now().getValue())
                .append(" ").append(time);

        return ZonedDateTime.parse(stringDate.toString(), formatter.withZone(ZoneOffset.ofHours(3)));
    }

    @Override
    public String parseUrl(Element e) {
        return e.select(".short-text").attr("href");
    }

    @Override
    public String parseAuthor(Element e) {
        return null;
    }

    @Override
    public String parseAnnounce(Element e) {
        return e.select(".short-text").attr("title");
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
        String cat = e.select(".short-text").attr("href");
        NewsCategory result = NewsCategory.OTHER;
        if (cat != null) {
            switch (cat.split("/")[1]) {
                case "lol":
                    result = NewsCategory.LOL;
                    break;
                case "dota2":
                    result = NewsCategory.DOTA;
                    break;
                case "hs":
                    result = NewsCategory.HS;
                    break;
                case "cs":
                    result = NewsCategory.CS;
                    break;
                case "ow":
                    result = NewsCategory.OW;
                    break;
            }
        }

        return result;
    }

    @Override
    public int parseCommentsCount(Element e) {
        return Integer.parseInt(e.children().last().text());
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
        if (prevElement.is("b")) {
            return  prevElement;
        } else {
            return resolveDateElement(prevElement);
        }
    }
}
