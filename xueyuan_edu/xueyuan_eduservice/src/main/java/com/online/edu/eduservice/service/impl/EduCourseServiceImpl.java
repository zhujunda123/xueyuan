package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.entity.dto.CoursePublishVo;
import com.online.edu.eduservice.entity.dto.CourseWebVo;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.entity.form.CourseQuery;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduChapterMapper;
import com.online.edu.eduservice.mapper.EduCourseDescriptionMapper;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoMapper videoMapper;
    @Autowired
    private EduChapterMapper chapterMapper;

    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Autowired
    private  EduCourseService eduCourseService;

    @Transactional
    @Override
    public CourseWebVo selectCourseWebVoById(String id) {
        //更新课程浏览数
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
        //获取课程信息
        return baseMapper.selectCourseWebVoById(id);
    }
    @Override
    public void publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("NORMAL");
        baseMapper.updateById(course);
    }

    /**
     * 根据讲师id查询当前讲师的课程列表
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> selectByTeacherId(String teacherId) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();

        queryWrapper.eq("teacher_id", teacherId);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Transactional
    @Override
    public void removeCourseById(String id) {

        //根据id删除所有视频
        QueryWrapper<EduVideo> queryWrapperVideo = new QueryWrapper<EduVideo>();
        queryWrapperVideo.eq("course_id", id);
        videoMapper.delete(queryWrapperVideo);

        //根据id删除所有章节
        QueryWrapper<EduChapter> queryWrapperChapter = new QueryWrapper<EduChapter>();
        queryWrapperChapter.eq("course_id", id);
        chapterMapper.delete(queryWrapperChapter);

        //删除课程详情
        eduCourseDescriptionMapper.deleteById(id);

        //根据id删除课程
        baseMapper.deleteById(id);
    }
    @Transactional
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        //保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescriptionService.updateById(courseDescription);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }



    @Override
    @Transactional
    public String insertCourseInfo(CourseInfoForm courseInfoForm) {
        //1课程基本信息到课程表
        EduCourse eduCourse= new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.insert(eduCourse);
        if(result==0){
            throw new EduException(20001,"添加课程失败");
        }

        //2课程描述添加到课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //获取描述信息
        String description = courseInfoForm.getDescription();
        eduCourseDescription.setDescription(description);
        //课程id
        String courseId = eduCourse.getId();
        eduCourseDescription.setId(courseId);

        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if(save){
            return courseId;
        }else {
            return null;
        }
    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {

        //从course表中取数据
        EduCourse course = baseMapper.selectById(id);
        if(course == null){
            throw new EduException(20001, "数据不存在");
        }

        //从course_description表中取数据
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        if(courseDescription == null){
            throw new EduException(20001, "数据不完整");
        }

        //创建courseInfoForm对象
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        BeanUtils.copyProperties(courseDescription, courseInfoForm);

        return courseInfoForm;
    }
}
