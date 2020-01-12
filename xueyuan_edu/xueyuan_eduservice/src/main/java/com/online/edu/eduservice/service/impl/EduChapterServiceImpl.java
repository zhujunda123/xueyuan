package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduChapter;
import com.online.edu.eduservice.entity.EduVideo;
import com.online.edu.eduservice.entity.dto.ChapterVo;
import com.online.edu.eduservice.entity.dto.VideoVo;
import com.online.edu.eduservice.mapper.EduChapterMapper;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoMapper videoMapper;

    @Override
    public void removeChapterById(String id) {

        //根据章节id删除所有视频
        QueryWrapper<EduVideo> queryWrapperVideo = new QueryWrapper<EduVideo>();
        queryWrapperVideo.eq("chapter_id", id);
        videoMapper.delete(queryWrapperVideo);

        //根据章节id删除章节
        baseMapper.deleteById(id);
    }


    @Override
    public List<ChapterVo> nestedList(String courseId) {

        //最终要的到的数据列表
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<ChapterVo>();

        //获取章节信息
        QueryWrapper<EduChapter> queryWrapperChapter = new QueryWrapper<EduChapter>();
        queryWrapperChapter.eq("course_id", courseId);
        queryWrapperChapter.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(queryWrapperChapter);

        //获取课时信息
        QueryWrapper<EduVideo> queryWrapperVideo = new QueryWrapper<EduVideo>();
        queryWrapperVideo.eq("course_id", courseId);
        queryWrapperVideo.orderByAsc("sort", "id");
        List<EduVideo> videos = videoMapper.selectList(queryWrapperVideo);

        //填充章节vo数据
        for (int i = 0; i < chapters.size(); i++) {
            EduChapter chapter = chapters.get(i);

            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);

            //填充视频vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<VideoVo>();
            for (int j = 0; j < videos.size(); j++) {

                EduVideo video = videos.get(j);
                if(chapter.getId().equals(video.getChapterId())){

                    //创建视频vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }
        return chapterVoArrayList;
    }
}
