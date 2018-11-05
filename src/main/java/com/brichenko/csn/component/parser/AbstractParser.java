package com.brichenko.csn.component.parser;

import com.brichenko.csn.entity.News;
import com.brichenko.csn.service.INewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Абстрактный парсер.
 */
@Component
public abstract class AbstractParser implements IAbstractParser {

    @Autowired
    public INewsService service;

    /**
     * Страница с новостями.
     */
    protected Document doc;

    /**
     * Форматтер даты.
     */
    protected DateTimeFormatter formatter;

    /**
     * Парсит новости с полученной страницы.
     */
    public void parseNews(){
        try {
            doc = Jsoup.connect(getNewsUrl()).get();

            service.saveNews(getRawNews(doc)
                    .stream()
                    .map(e -> createNews(e))
                    .collect(Collectors.toList()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создает новость.
     *
     * @param e - dom-элемент.
     * @return - сформированная новость.
     */
    private News createNews(Element e){
        News news = new News();
        news.setExternalId(parseId(e));
        news.setTitle(parseTitle(e));
        news.setBody(parseBody(e));
        news.setUrl(parseUrl(e));
        news.setAuthor(parseAuthor(e));
        news.setCommentsCount(parseCommentsCount(e));
        news.setPublished(parsePublished(e));
        news.setAnnonce(parseAnnounce(e));
        news.setImage(parseImage(e));
        news.setCategory(parseCategory(e));
        news.setHost(getHostUrl());
        news.setHostName(getHostName());

        return news;
    }

}
