package com.brichenko.csn.service;

import com.brichenko.csn.entity.News;
import com.brichenko.csn.entity.enums.NewsCategory;

import java.util.List;

/**
 * Сервис для работы с новостями.
 */
public interface INewsService {

    /**
     * Возвращает список новостей.
     *
     * @return - список новостей.
     */
    List<News> getNews();

    /**
     * Возвращает список новостей для переданной категории.
     *
     * @param category - запрашиваемая категория новостей.
     * @return - список новостей.
     */
    List<News> getNewsByCategory(NewsCategory category);

    /**
     * Сохраняет список новостей в хранилище.
     *
     * @param news - список новостей для сохранения.
     * @return - список сохраненных новостей.
     */
    List<News> saveNews(List<News> news);
}
