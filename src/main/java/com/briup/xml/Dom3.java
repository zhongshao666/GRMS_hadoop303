package com.briup.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dom3 {
    public static void main(String[] args) throws DocumentException {
        SAXReader r = new SAXReader();
        //使用解析器解析
        Document doc = r.read("src/main/java/com/briup/xml/user1.xml");//src/main/java/
        //硬盘xml加载到主存中
        Element root = doc.getRootElement();



        show(root);
    }

    public static void show(Element root) {
        List<Element> list = root.elements();
        Map<String, Integer> map = new HashMap<>();
        if (list.size() > 0) {
            for (Element e : list) {
                List<Attribute> attributes = e.attributes();
                for (Attribute att : attributes) {
                    String name = att.getValue().trim();
                    System.out.print(name + "\t");
                    String money = e.getTextTrim();
                    System.out.print(money + "\n");
                    if (!map.containsKey(name)) {
                        map.put(name, Integer.valueOf(money));
                    } else
                        map.put(name, map.get(name) + Integer.valueOf(money));
                }
//                show(e);
            }
        }
        System.out.println(map);
    }
}
