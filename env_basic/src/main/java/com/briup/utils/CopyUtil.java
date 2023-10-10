package com.briup.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;


public class CopyUtil {
    public static <T> T copy(T t,String ... ignore) throws Exception {
        Class<?> aClass = t.getClass();
        Object result = aClass.newInstance();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            boolean flag = true;
            for (String s : ignore) {
                if(name.equals(s)){
                    flag = false;
                }
            }
            if(flag){
                declaredField.set(result,declaredField.get(t));
            }

        }

        return (T) result;
    }

    public static void main(String[] args) throws Exception {
        Student s1 = new Student(1, "tom", 10);
        Student s = new Student();
        BeanUtils.copyProperties(s1,s);
        System.out.println("s = " + s);
    }
}
class Student { // 克隆标志
    int id;
    String name;
    int age;
    //    User user; id username password
    public Student(){}
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

