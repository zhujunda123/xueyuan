package com.online.edu.eduservice.controller;



import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.EduUser;
import com.online.edu.eduservice.service.EduUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-24
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduUserController {
    @Autowired
    private EduUserService eduUserService;
    @GetMapping
    public R getAllUser(HttpServletRequest request){
        String s = request.getHeader("x-token");
        System.out.println(s);
        List<EduUser> list = eduUserService.list(null);
        return R.ok().data("list",list);
    }
    @DeleteMapping("{id}")
    public R removeById(@PathVariable(value = "id")Integer id, HttpServletRequest request){

        String roles = (String)request.getAttribute("roles");

        boolean b = eduUserService.removeById(id);
        if(b){
            return R.ok().message("删除成功");
        }else {
            return R.error();
        }
    }
    //根据id查询用户
    @GetMapping("{id}")
    public R getUserById(@PathVariable(value = "id")Integer id){
        EduUser user = eduUserService.getById(id);
        return R.ok().data("user",user);
    }


    @PutMapping("updateEduUser/{id}")
    public R updateById(
            @PathVariable String id,
            @RequestBody EduUser eduUser) {


        boolean b = eduUserService.updateById(eduUser);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("addUser")
    public R addUser(@RequestBody EduUser eduUser) {
        System.out.println(eduUser);
        boolean save = eduUserService.save(eduUser);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}

