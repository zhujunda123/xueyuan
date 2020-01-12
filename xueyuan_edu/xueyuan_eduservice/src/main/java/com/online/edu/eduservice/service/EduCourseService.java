package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.CoursePublishVo;
import com.online.edu.eduservice.entity.dto.CourseWebVo;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.form.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-27
 */
public interface EduCourseService extends IService<EduCourse> {

    String insertCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void removeCourseById(String id);

    CoursePublishVo getCoursePublishVoById(String id);

    void publishCourseById(String id);

    List<EduCourse> selectByTeacherId(String id);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam);
    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectCourseWebVoById(String id);
}
