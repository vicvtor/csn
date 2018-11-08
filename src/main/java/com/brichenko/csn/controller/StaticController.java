package com.brichenko.csn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер доступа к статичным ресурсам.
 */
@Controller
public class StaticController {

    /**
     * Главная страница.
     * @return - главная страница.
     */
    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    /**
     * Загрушка для смены локали.
     * @return - главная страница.
     */
    @GetMapping("/lang")
    public String changeLocale(){
        return "index";
    }

}
