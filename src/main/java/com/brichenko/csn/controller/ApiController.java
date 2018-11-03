package com.brichenko.csn.controller;

import com.brichenko.csn.entity.News;
import com.brichenko.csn.entity.enums.NewsCategory;
import com.brichenko.csn.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер доступа к API.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    INewsService service;

    @Autowired
    MessageSource messageSource;

    /**
     * Возвращает карту категорий новостей клиенту.
     *
     * @param locale - текущая локаль сессии.
     * @return - карта категорий.
     */
    @GetMapping("/cats")
    public Map<String, String> getCategories(Locale locale){
        return Arrays.stream(NewsCategory.values())
                .collect(Collectors.toMap(Enum::name, x-> messageSource.getMessage(x.name(), null, locale)));
    }

    /**
     * Возвращает список новостей для заданной категории.
     * Если категория не задана, вернутся новости без учета категорий.
     *
     * @param category - категория новостей для поиска.
     * @return - список новостей.
     */
    @GetMapping("/news/{category}")
    public List<News> parseCyberSport(@PathVariable(required = false) String category) {

        if (category != null && !category.equals("ALL")) {
            NewsCategory cat = NewsCategory.valueOf(category);
            return service.getNewsByCategory(cat);
        }

        return service.getNews();
    }

}
