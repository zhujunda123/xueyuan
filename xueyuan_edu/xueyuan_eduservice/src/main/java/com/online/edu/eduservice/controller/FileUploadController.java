package com.online.edu.eduservice.controller;//package com.online.edu.eduservice.controller;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/file")
//public class FileUploadController {
//
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
//
//    @PostMapping("/upload")
//    public String upload(@RequestParam("file")MultipartFile uploadFile, HttpServletRequest req) {
//        // 上传的文件将保存在项目运行目录下的 uploadFile 文件夹，
//        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
//        System.out.println(realPath);
//
//        // 并且在 uploadFile 文件夹中通过日期对上传的文件归类保存
//        // 比如：/uploadFile/2019/06/06/32091e5f-c9e9-4506-9567-43e724f1fe37.png
//        String format = sdf.format(new Date());
//        File folder = new File(realPath + format);
//        if (!folder.isDirectory()) {
//            folder.mkdirs();
//        }
//
//        // 对上传的文件重命名，避免文件重名
//        String oldName = uploadFile.getOriginalFilename();
//        String newName = UUID.randomUUID().toString()
//                + oldName.substring(oldName.lastIndexOf("."), oldName.length());
//        try {
//            // 文件保存
//            uploadFile.transferTo(new File(folder, newName));
//
//            // 返回上传文件的访问路径
//            String filePath = req.getScheme() + "://" + req.getServerName()
//                    + ":" + req.getServerPort() + "/uploadFile/" + format + newName;
//            return filePath;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "上传失败!";
//    }
//}

import com.online.edu.common.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/eduservice")
@CrossOrigin
public class FileUploadController {
    private static  final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/uploadFile/";

    @PostMapping("/upload")
    public R upload(@RequestParam("file")MultipartFile uploadFile, HttpServletRequest request){
        if(uploadFile.isEmpty()){
            //返回选择文件提示
            return R.error().message("请选择上传文件");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("xueyuan_eduservice/src/main/resources/" + UPLOAD_PATH_PREFIX);
        logger.info("-----------上传文件保存的路径【"+ realPath +"】-----------");
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + format);
        logger.info("-----------存放上传文件的文件夹【"+ file +"】-----------");
        logger.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【"+ file.getAbsolutePath() +"】-----------");
        if(!file.isDirectory()){
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = uploadFile.getOriginalFilename();
        logger.info("-----------文件原始的名字【"+ oldName +"】-----------");
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        logger.info("-----------文件要保存后的新名字【"+ newName +"】-----------");
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
            logger.info("-----------【"+ filePath +"】-----------");

            return R.ok().data("url",filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.error().message("上传失败");
    }


}
