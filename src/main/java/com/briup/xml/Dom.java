package com.briup.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class Dom {
    public static void main(String[] args) throws DocumentException {
        dom1();
    }

    public static void dom1() throws DocumentException {
        SAXReader r = new SAXReader();
        //使用解析器解析
        Document doc = r.read("src/main/java/com/briup/xml/user.xml");//src/main/java/
        //硬盘xml加载到主存中
        Element root=doc.getRootElement();
        show(root);
/*        List<Element> elements = root.elements();
        for (Element e:elements) {
            System.out.println(e.getName());
            Element id=e.element("id");
            show(id);
            List<Element> ids=e.elements("id");
            ids.forEach(System.out::println);
        }*/
    }

    public static void show(Element root){
        List<Element> list =root.elements();
        if(list.size()>0){
            for (Element e:list) {
                System.out.println(e.getName());
                show(e);
            }
        }
    }

}
