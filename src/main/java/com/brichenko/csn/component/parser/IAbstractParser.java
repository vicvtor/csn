package com.brichenko.csn.component.parser;

import com.brichenko.csn.entity.enums.NewsCategory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;

/**
 * Интерфейс парсера.
 */
public interface IAbstractParser {

    /**
     * Возвращает адрес, на котором публикуются новости.
     *
     * @return - адрес страницы с новостями.
     */
    String getNewsUrl();

    /**
     * Возвращает имя хоста, с которого будет производиться парсинг новостей.
     *
     * @return - имя хоста.
     */
    String getHostName();

    /**
     * Возвращает список dom-элементов с содержимым новостей из переданного документа.
     *
     * @param doc - документ с новостями.
     * @return - список новостей.
     */
    Elements getRawNews(Document doc);

    /**
     * Парсит и взвращает заголовок новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - заголовок новости.
     */
    String parseTitle(Element e);

    /**
     * Парсит и взвращает дату публикации новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - дата публикации новости.
     */
    LocalDateTime parsePublished(Element e);

    /**
     * Парсит и взвращает ссылку на новость из dom-элемента.
     * @param e - dom-элемент.
     * @return - ссылка.
     */
    String parseUrl(Element e);

    /**
     * Парсит и взвращает автора новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - автор.
     */
    String parseAuthor(Element e);

    /**
     * Парсит и взвращает анонс новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - анонс.
     */
    String parseAnnounce(Element e);

    /**
     * Парсит и взвращает содержимое новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - содержимое.
     */
    String parseBody(Element e);

    /**
     * Парсит и взвращает идентификатор новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - идентификатор.
     */
    String parseId(Element e);

    /**
     * Парсит и взвращает изображение новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - свзанное изображение.
     */
    String parseImage(Element e);

    /**
     * Парсит и взвращает категорию новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - категория.
     */
    NewsCategory parseCategory(Element e);

    /**
     * Парсит и взвращает количество комментариев у новости из dom-элемента.
     * @param e - dom-элемент.
     * @return - число комментариев.
     */
    int parseCommentsCount(Element e);

}
