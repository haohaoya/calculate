package util;

import java.lang.reflect.Constructor;
import java.util.HashMap;

//目的是为了管理对象的产生
//对象的控制权交给当前类来负责    IOC控制反转
public class MySpring {

    //属性 为了存储所有被管理的对象
    private static HashMap<String,Object> beanBox = new HashMap<>();

    public static <T>T getBean(String className) {
        T obj = null;
        try {
            obj = (T)beanBox.get(className);
            if(obj == null) {
                Class clazz = Class.forName(className);
                obj = (T)clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
