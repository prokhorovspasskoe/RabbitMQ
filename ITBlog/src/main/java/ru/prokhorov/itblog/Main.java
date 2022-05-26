package ru.prokhorov.itblog;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Blogger blogger = new Blogger();

        do {
            System.out.print("Write your article: ");
            String topic = in.nextLine();
            String routingKey = topic.substring(0, 3);
            blogger.setRoutingKeyAndMessage(routingKey, topic.substring(4));
        }while(true);
    }
}