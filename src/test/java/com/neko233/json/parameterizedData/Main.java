package com.neko233.json.parameterizedData;


import com.neko233.json.typeRef.GenericTypeFactory;
import com.neko233.json.typeRef.JsonParameterizedTypeImpl;

import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        String userClassName = User.class.getName();
        Type genericType = new JsonParameterizedTypeImpl(userClassName, String.class);

        User<String> user = GenericTypeFactory.createInstance(genericType);
        user.data = "a";
        System.out.println(user); // Output: User<String>
    }


    public static class User<T> {
        // Add your User class implementation here

        T data;

        @Override
        public String toString() {
            return "User{" +
                    "data=" + data +
                    '}';
        }
    }
}
