package com.briup.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class Dom1 {
    public static void main(String[] args) throws DocumentException {
        SAXReader r = new SAXReader();
        //使用解析器解析
        Document doc = r.read("src/main/java/com/briup/xml/user.xml");//src/main/java/
        //硬盘xml加载到主存中
        Element root=doc.getRootElement();
        //获取标签属性
        List<Attribute> alist = root.attributes();

        for (Attribute att:alist) {
            //获取属性名
            String name = att.getName();
            String value = att.getValue();
            System.out.println("name = " + name);
            System.out.println("value = " + value);
        }

        System.out.println("---------------------");
        Attribute name = root.attribute("name");

        String value = root.attributeValue("name");
        System.out.println("name = " + name);
        System.out.println("value = " + value);


    }
}
