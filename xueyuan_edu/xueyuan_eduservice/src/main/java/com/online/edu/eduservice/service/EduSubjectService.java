package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importSubject(MultipartFile file);

    List<SubjectNestedVo> nestedList();


    //删除分类
    boolean deleteSubjectById(String id);

    //添加一级分类
    boolean saveOneLevel(EduSubject eduSubject);

    //添加二级分类
    boolean saveTwoLevel(EduSubject eduSubject);

}
