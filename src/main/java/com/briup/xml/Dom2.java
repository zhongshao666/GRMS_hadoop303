package com.briup.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom2 {
    public static void main(String[] args) throws DocumentException {
        SAXReader r = new SAXReader();
        //使用解析器解析
        Document doc = r.read("src/main/java/com/briup/xml/user.xml");//src/main/java/
        //硬盘xml加载到主存中
        Element root=doc.getRootElement();
        String stringValue = root.getStringValue();

        String text = root.getText();


        String textTrim = root.getTextTrim();

        System.out.println("stringValue = " + stringValue);
        System.out.println("-------------");
        System.out.println("text = " + text);
        System.out.println("-------------");
        System.out.println("textTrim = " + textTrim);

    }
}
