package cn.huanji.utils;

import cn.huanji.domain.Student;


import java.util.*;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.core.date.DateUtil;


public class ExcelUtil {

    public static String exportExcel(List<Student> students){
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Student student:students){
            Map<String,Object> map = new HashMap<>();
            map.put("姓名",student.getName());
            map.put("性别",student.getSex());
            map.put("地址",student.getAddress());
            resultList.add(map);
        }

        String fileName = DateUtil.format(new Date(),"yyyy-MM-dd");
        String url = "e:/excel/"+fileName+".xlsx";
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(url);
        writer.write(resultList,true);
        writer.close();
        return url;
    }
}
