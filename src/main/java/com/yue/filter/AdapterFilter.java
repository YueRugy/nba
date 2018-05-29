package com.yue.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by yue on 2017/9/8
 */
public abstract class AdapterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public abstract void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {

    }
}
