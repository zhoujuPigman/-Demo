package cn.huanji;

import cn.huanji.domain.Student;
import cn.huanji.utils.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

public class ExcelDemo {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++){
            students.add(new Student("小明"+i,"男","广东"));
            students.add(new Student("小红"+i,"女","广东"));
            students.add(new Student("小强"+i,"男","湖南"));
        }
        String url = ExcelUtil.exportExcel(students);
        if (url.isEmpty()){
            System.out.println("导出失败");
        }
    }


}
