package com.online.edu.eduservice.mapper;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.eduservice.entity.dto.CoursePublishVo;
import com.online.edu.eduservice.entity.dto.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-12-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo selectCoursePublishVoById(String id);
    CourseWebVo selectCourseWebVoById(String courseId);
}
