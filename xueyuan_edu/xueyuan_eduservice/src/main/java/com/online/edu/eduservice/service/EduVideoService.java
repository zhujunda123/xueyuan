package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-11
 */
public interface EduVideoService extends IService<EduVideo> {

    void saveVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    void removeVideoById(String id);
}
