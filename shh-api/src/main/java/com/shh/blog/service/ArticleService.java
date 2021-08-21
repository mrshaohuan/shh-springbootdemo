package com.shh.blog.service;

import com.shh.blog.vo.Result;
import com.shh.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

}
