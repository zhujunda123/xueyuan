package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-17
 */
public interface EduChapterService extends IService<EduChapter> {

    void removeChapterById(String id);

    List<ChapterVo> nestedList(String courseId);
}
