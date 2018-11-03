package com.brichenko.csn.entity;

import com.brichenko.csn.entity.enums.NewsCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Сущность новости.
 */
@Entity
@Table(name="News")
@Getter
@Setter
@NoArgsConstructor
public class News {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Идентификатор на сайте.
     */
    private String externalId;

    /**
     * Заголовок.
     */
    @NotNull
    private String title;

    /**
     * Краткое содержание.
     */
    private String annonce;

    /**
     * Содержание.
     */
    private String body;

    /**
     * Связанное изображение.
     */
    private String image;

    /**
     * Дата публикации.
     */
    @NotNull
    private LocalDateTime published;

    /**
     * Дата загрузки.
     */
    private LocalDateTime created = LocalDateTime.now();

    /**
     * Автор.
     */
    private String author;

    /**
     * Ссылка на новость.
     */
    @NotNull
    private String url;

    /**
     * Имя Хоста
     */
    @NotNull
    private String host;

    /**
     * Категория новости.
     */
    @NotNull
    private NewsCategory category;

    /**
     * Счетчик комментариев.
     */
    private int commentsCount;
}
