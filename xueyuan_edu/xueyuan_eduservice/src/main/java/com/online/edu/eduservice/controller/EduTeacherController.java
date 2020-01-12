package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.EduUser;
import com.online.edu.eduservice.entity.QueryTeacher;
import com.online.edu.eduservice.entity.User;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.eduservice.mapper.EduUserMapper;
import com.online.edu.eduservice.service.EduTeacherService;
import com.online.edu.eduservice.service.EduUserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-17
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EduUserService userService;

//{"code":20000,"data":{"token":"admin"}}

    @PostMapping("login")
    public R login(@RequestBody User user) {
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());


        EduUser user1 = userService.findByUser(user);
        System.out.println(user1);
        if (user1 == null)
            return R.error();
        String token;
        String name=user.getUsername();

        if(name.equals("admin")){
      token = jwtUtil.createJWT(user1.getId().toString(), user.getUsername(), "[admin]");
        }else{
             token = jwtUtil.createJWT(user1.getId().toString(), user.getUsername(), "[guest]");
        }
        System.out.println(token + "---------------");
        return R.ok().data("token", token);
    }

    //{"code":20000,"data":{"roles":["admin"],"name","admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    @GetMapping("info")
    public R info(@RequestParam String token, HttpServletRequest request) {
        Claims claims = jwtUtil.parseJWT(token);
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("IssuedAt:" + claims.getIssuedAt());
        String roles = (String) claims.get("roles");
        System.out.println(roles);

        System.out.println(token);
        return R.ok()
                .data("roles", roles)
                .data("name", claims.getSubject())
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

//        return R.ok()
//                .data("roles", "[admin]")
//                .data("name", "admin")
//                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


    @PostMapping("logout")
    @ApiOperation(value = "用户登出")
    public R logout(){
        return R.ok();
    }

    //7根据id修改的方法
    @ApiOperation(value = "根据id修改讲师")
    @PutMapping("updateTeacher/{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "eduTeacher", value = "讲师", required = true)
            @RequestBody EduTeacher eduTeacher) {


        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //6根据id查询讲师
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher", eduTeacher);
    }

    //5添加讲师方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //4多条件组合查询带分页
    @PostMapping("moreConditionPageList/{page}/{limit}")
    public R getMoreConditionPageList(@PathVariable Long page, @PathVariable Long limit,
                                      @RequestBody(required = false) QueryTeacher queryTeacher) {

        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page, limit);
        eduTeacherService.pageListCondition(pageTeacher, queryTeacher);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("items", records);
    }


    //3分页查询讲师列表
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page, @PathVariable Long limit) {
//        try {
//            int i = 8 / 0;
//        } catch (Exception e) {
//            throw new EduException(20001, "操作异常");
//        }
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page, limit);
        eduTeacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    //1查询都有讲师的功能

//    @GetMapping
//    public List<EduTeacher>getAllTeacherList(){
//
//        List<EduTeacher> list = eduTeacherService.list(null);
//
//        return  list;
//    }

    @GetMapping
    public R getAllTeacherList() {

        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{teacherId}")
    public R getById(
            @ApiParam(name = "teacherId", value = "讲师ID", required = true)
            @PathVariable String teacherId){

        //查询讲师信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);

        return R.ok().data("teacher", teacher);
    }
    @DeleteMapping("{id}")
    public  R removeById(@PathVariable String id) {

        boolean b = eduTeacherService.removeById(id);
        if(b){
        return R.ok().message("删除成功");
        }else
            return R.error();

    }

}

