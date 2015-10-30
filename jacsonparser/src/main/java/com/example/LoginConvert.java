package com.example;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LoginConvert extends JsonDeserializer<LoginStatus> {

    @Override
    public LoginStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        int code = 0;
        String msg="";
        User user=null;
        while (jp.nextToken()!= JsonToken.END_OBJECT){
            if(jp.getCurrentToken()==JsonToken.FIELD_NAME){
                String filedName=jp.getCurrentName();

                jp.nextToken();
                if("code".equalsIgnoreCase(filedName)) {
                    code = jp.readValueAs(Integer.class);
                }
                else if("msg".equalsIgnoreCase(filedName)){
                    if(code==1000){
                        user=jp.readValueAs(User.class);
                    }
                    else {
                        msg = jp.readValueAs(String.class);
                    }
                }

            }
        }
        LoginStatus loginStatus=new LoginStatus(code,msg,user);

        return loginStatus;
    }
}
