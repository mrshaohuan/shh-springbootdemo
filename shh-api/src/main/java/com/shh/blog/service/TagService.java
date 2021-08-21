package com.shh.blog.service;

import com.shh.blog.vo.Result;
import com.shh.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
