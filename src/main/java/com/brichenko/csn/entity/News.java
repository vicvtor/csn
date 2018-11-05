package com.brichenko.csn.entity;

import com.brichenko.csn.entity.enums.NewsCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
    private ZonedDateTime published;

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
     * Имя хоста.
     */
    @NotNull
    private String host;

    /**
     * Псевдоним хоста.
     */
    @NotNull
    private String hostName;

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
