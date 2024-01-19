package util;

import lombok.Getter;
import lombok.Setter;

public class Login {
    private static UserType userType;

    public static void setUser(UserType type){
        userType=type;
    }
    public static void clearType(){
        userType=null;
    }

    public static UserType getUser(){
        return userType;
    }
}
