package com.brichenko.csn.repository;

import com.brichenko.csn.entity.News;
import com.brichenko.csn.entity.enums.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с новостями.
 */
public interface INewsRepository extends JpaRepository<News, Long> {

    /**
     * Врзвращает новости для запрашиваемой категории из БД.
     *
     * @param category - категория.
     * @return - список новостей.
     */
    List<News> findByCategoryOrderByPublishedDesc(NewsCategory category);


    List<News> findByTitle(String title);

}
