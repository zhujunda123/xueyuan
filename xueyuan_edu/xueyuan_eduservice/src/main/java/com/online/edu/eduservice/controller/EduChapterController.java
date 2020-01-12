package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.dto.ChapterVo;
import com.online.edu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-17
 */
@Api(description="课程章节管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "新增章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){

        chapterService.save(chapter);
        return R.ok();
    }
    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }
    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){

        chapter.setId(id);
        chapterService.updateById(chapter);
        return R.ok();
    }
    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        chapterService.removeChapterById(id);
        return R.ok();
    }

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("nestedList/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }
}

