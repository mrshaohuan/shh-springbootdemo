package com.shh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shh.blog.dao.mapper.ArticleMapper;
import com.shh.blog.dao.pojo.Article;
import com.shh.blog.dos.Archives;
import com.shh.blog.service.ArticleService;
import com.shh.blog.service.SysUserService;
import com.shh.blog.service.TagService;
import com.shh.blog.vo.ArticleVo;
import com.shh.blog.vo.Result;
import com.shh.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {

        /**
         * 1.分页查询article文章列表
         */
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page,queryWrapper);
        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articleList,false,false);
        return Result.success(articleVoList);
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articleList,false,false);
        return Result.success(articleVoList);
    }

    @Override
    public Result listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }
}
