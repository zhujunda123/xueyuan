package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.EduUser;
import com.online.edu.eduservice.entity.User;
import com.online.edu.eduservice.mapper.EduUserMapper;
import com.online.edu.eduservice.service.EduUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-24
 */
@Service
public class EduUserServiceImpl extends ServiceImpl<EduUserMapper, EduUser> implements EduUserService {

    @Autowired
    EduUserMapper eduUserMapper;
    public EduUser findByUser(User user) {
        QueryWrapper<EduUser> wrapper = new QueryWrapper<EduUser>();
        if(!StringUtils.isEmpty(user.getUsername())){
            wrapper.eq("username",user.getUsername());
        }
        if(!StringUtils.isEmpty(user.getUsername())){
            wrapper.eq("password",user.getPassword());
        }

        EduUser eduUser = eduUserMapper.selectOne(wrapper);

        return eduUser;
    }
}
