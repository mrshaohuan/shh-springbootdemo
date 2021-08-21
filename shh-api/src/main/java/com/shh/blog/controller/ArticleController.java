package com.shh.blog.controller;

import com.shh.blog.service.ArticleService;
import com.shh.blog.vo.Result;
import com.shh.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章返回列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result articles(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 2;

        return articleService.hotArticle(limit);
    }

    @PostMapping("new")
    public Result newArticles(){
        int limit = 2;

        return articleService.newArticle(limit);
    }

    @PostMapping("listArchives")
    public Result listArchives(){

        return articleService.listArchives();
    }
}
