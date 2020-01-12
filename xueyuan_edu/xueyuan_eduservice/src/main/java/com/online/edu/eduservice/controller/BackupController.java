package com.online.edu.eduservice.controller;

import com.online.edu.common.R;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eduservice/table/backups/")
@CrossOrigin
public class BackupController {


    @RequestMapping(value = "{dbName}",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public R databasebackup(@PathVariable(value = "dbName") String dbName ,HttpServletResponse response) throws Exception{
//        String filePath="D:\\back\\";
        String filePath=System.getProperty("user.dir")+"\\xueyuan_eduservice\\src\\main\\resources\\backup\\";
        /*String fileName="批量上传模板.xlsx";*/
//        String dbName="teacher";
        try {
            Process process = Runtime.getRuntime().exec(
                    "cmd  /c  mysqldump -u root -proot "+" xueyuan_edu " + dbName + " > "
                            + filePath + "/" + dbName+new java.util.Date().getTime()
                            + ".sql");
            //备份的数据库名字为teacher，数据库连接和密码均为root
            System.out.println(filePath);
            System.out.println("success!!!");
            return R.ok();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
    }
}
