package com.briup.GRMS;

/*
public class Test {
    public static void main(String[] args) {
        int a = 11, b = 20;
        if (a-- == 10 && b++ == 21) {
            System.out.println("ok");
        }
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    } }*/


/*
public class Test {
    public static void main(String... args) {
        short a = 1, b = 2; // 第一行
        a = (short) (a + 1); // 第二行
        b++; // 第三行
        System.out.println(a); // 第四行
        System.out.println(b); // 第五行
    }
    public void a(){}
    public int a111(int a){return 0;}//返回值必须相同
    public void a(int a){}

}*/

//内部类可以直接访问外部成员变量，包括私有
//外部要访问内部类的成员，必须创建对象。

/*
class A {
    private String name = "小李";

    //静态代码块（static修饰,只执行一次）-构造代码块 -构造方法 -局部代码块
    static {
        System.out.println("静态代码块,只执行一次");
    }
    //构造代码块,每次调用构造方法执行前，都会优先执行构造代码块。作用：可以把多个构造方法中的共同代码放到一起
    {
        int x =10;
        System.out.println(x);
    }
    public A() {
        System.out.println(getName());
    }

    public String getName() {
        return name;
    }
}

public class Test extends A {
    private String name = "小红";

    {
        int x =20;
        System.out.println(x);
    }
    public Test() {
        System.out.println(getName());
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        new Test();
        System.out.println("--------------------------");
        new Test();
    }
}*/


/*
public class Test{
    public static void main(String[] args) {
        Integer a = 127;
        Integer b = 125;
        Integer c = 2;
        Integer d = b + c;
        Integer f = 128;
        Integer g = 128;
        System.out.println(d == a);
        System.out.println(f == g);
    }
}*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class A{}
class B extends A{}

public class Test{
    public static void main(String[] args) {
        List<A> list = new ArrayList<>();
//        list.add(new A(),new B());
        list= Arrays.asList(new A(),new B());

    }
}