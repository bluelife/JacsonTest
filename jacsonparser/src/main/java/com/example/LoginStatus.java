package com.example;

/**
 * Created by slomka.jin on 2015/10/29.
 */
public class LoginStatus {
    public int code;
    public String msg;
    public User user;

    public LoginStatus(int code,String msg,User user){
        this.code=code;
        this.msg=msg;
        this.user=user;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj==null||obj.getClass()!=getClass())
            return false;
        LoginStatus status=(LoginStatus) obj;
        boolean codeIsEqual=code==status.code;
        boolean msgIsEqual=!(msg!=null?!msg.equals(status.msg):status.msg!=null);
        boolean userIsEqual=!(user!=null?!user.equals(status.user):status.user!=null);
        return codeIsEqual&&msgIsEqual&&userIsEqual;
    }

    @Override
    public int hashCode() {
        int result=17;
        int msgHash=msg==null?0:msg.hashCode();
        result=result*31+msgHash;
        result=result*31+code;
        return result;
    }
}
