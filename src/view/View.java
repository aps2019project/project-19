package view;

import java.util.Scanner;

public class View {
    public static void printError(ErrorType errorType){
        System.out.println(errorType.getMessage());
    }
}
