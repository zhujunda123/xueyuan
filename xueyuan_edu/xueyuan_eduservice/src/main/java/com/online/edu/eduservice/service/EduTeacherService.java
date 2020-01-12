package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.QueryTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-17
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
