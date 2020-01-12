package com.online.edu.eduservice.controller;

import com.online.edu.common.R;
import com.online.edu.eduservice.mapper.TableDao;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduservice/table")
@CrossOrigin
public class TableController {

    @Resource
    TableDao tableDao;

    @RequestMapping("/list")
    public R list() {

        return R.ok().data("list",tableDao.listTable());
    }

//    @RequestMapping("/columns")
//    public ServiceRowsResult info(String tableName) {
//        ServiceRowsResult result = new ServiceRowsResult(true);
//        result.setPage(tableDao.listTableColumn(tableName));
//        return result;
//    }
}