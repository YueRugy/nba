package com.yue.dao;

import com.yue.entity.EmptyUrl;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yue on 2018/6/1
 */
public interface EmptyUrlDao extends JpaRepository<EmptyUrl, Integer> {
}
