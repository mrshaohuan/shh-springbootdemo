package com.shh.blog.service;

import com.shh.blog.vo.Result;
import com.shh.blog.vo.params.LoginParam;

public interface LoginService {

    Result login(LoginParam loginParam);
}
