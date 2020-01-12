package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-24
 */
public interface EduUserService extends IService<EduUser> {

    EduUser findByUser(User user);



}
