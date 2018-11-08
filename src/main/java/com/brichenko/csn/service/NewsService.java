package com.brichenko.csn.service;

import com.brichenko.csn.entity.News;
import com.brichenko.csn.entity.enums.NewsCategory;
import com.brichenko.csn.repository.INewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService implements INewsService{

    @Autowired
    INewsRepository repository;

    @Override
    public List<News> getNews() {
        return repository.findAll(new Sort(Sort.Direction.DESC, "published"));
    }

    @Override
    public List<News> getNewsByCategory(NewsCategory category) {
        return repository.findByCategoryOrderByPublishedDesc(category);
    }

    @Override
    public List<News> saveNews(List<News> news) {

        List<News> savedNews = new ArrayList<>();

        for (News n : news) {
            if  (repository.findByUrl(n.getUrl()).isEmpty()) {
                savedNews.add(repository.save(n));
                System.out.print("News " + n.getTitle() + " from host " + n.getHost() + " saved.");
            }
        }

        return savedNews;
    }
}
