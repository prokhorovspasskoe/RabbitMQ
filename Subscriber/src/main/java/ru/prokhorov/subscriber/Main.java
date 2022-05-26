package ru.prokhorov.subscriber;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Subscriber subscriber;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String routingKey = "";
        String exitKey;
        initialize();

        do {
            System.out.print("Enter command: ");
            String command = in.nextLine();

            if(routingKey.equals("ext")){
                System.exit(0);
            }
            if(routingKey.equals("uns")){
                System.out.println("Unsubscribe!");
                routingKey = "";
                initialize();
                continue;
            }
            routingKey = command.substring(10, 13);
            subscriber.setRoutingKeyAndBind(routingKey);

        }while(true);
    }

    private static void initialize(){
        subscriber = new Subscriber();
        try {
            subscriber.driverCallback();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
