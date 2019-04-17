package view;

import java.util.Scanner;

public class View {
    private static final View VIEW = new View();
    public static View getInstance(){
        return VIEW;
    }
    private View(){
    }
    public void printError(ErrorType errorType){
        System.out.println(errorType.getMessage());
    }
    public void printEnterPassword(){
        System.out.println("Enter your password:");
    }
    public void show(String string){
        System.out.println(string);
    }
}
