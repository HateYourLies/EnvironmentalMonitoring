package com.briup.utils;

import com.briup.common.entity.Student;
import com.briup.common.utils.CopyUtil;
import org.junit.Test;


public class TestCopyUtil {
    @Test
    public void test() throws Exception {
        Student s = new Student(1,"tom",18);
        Student copy = CopyUtil.copy(s,"age","name");
        System.out.println("copy = " + copy);

//        System.out.println(Integer.parseInt("10", 16));
//        System.out.println(Integer.parseInt("8", 16));
//        System.out.println(Integer.parseInt("2", 16));


    }
}
