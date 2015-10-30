package com.example;

/**
 * Created by slomka.jin on 2015/10/29.
 */
public class User {
    public String uid;
    public String tag;


    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj==null||obj.getClass()!=getClass())
            return false;
        User other=(User) obj;
        boolean uidEqual=!(uid!=null?!uid.equals(other.uid):other.uid!=null);
        boolean tagEqual=!(tag!=null?!tag.equals(other.tag):other.tag!=null);
        return uidEqual&&tagEqual;
    }
}
