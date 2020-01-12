package com.online.edu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.dto.ChapterVo;
import com.online.edu.eduservice.entity.dto.CourseWebVo;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontCourse")
@Api(description="前台课程模块")
@CrossOrigin
public class FrontEduCourse {
    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
          @PathVariable Long limit){

        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);

        Map<String, Object> map = courseService.pageListWeb(pageParam);

        return  R.ok().data(map);
    }
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectCourseWebVoById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }
}

