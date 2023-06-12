package com.neko233.json.exception;


import com.neko233.json.utils.StringUtilsForJson;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public class DeserializeJsonException extends RuntimeException {


    public DeserializeJsonException(Exception e,
                                    String msg) {
        super(msg, e);
    }

    public DeserializeJsonException(Exception e,
                                    String msg,
                                    Object... objects) {
        super(StringUtilsForJson.format(msg, objects), e);
    }

    public DeserializeJsonException(Exception e) {
        super(e);
    }
}
