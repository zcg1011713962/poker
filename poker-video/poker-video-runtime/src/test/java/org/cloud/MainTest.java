package org.cloud;

import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String a = in.next();
            System.out.print(a);
        }
        System.out.println("end");
    }
}
