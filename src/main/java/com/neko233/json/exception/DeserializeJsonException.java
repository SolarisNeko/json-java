package com.neko233.json.exception;

import com.neko233.skilltree.commons.core.base.StringUtils233;

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
        super(StringUtils233.format(msg, objects), e);
    }

    public DeserializeJsonException(Exception e) {
        super(e);
    }
}
