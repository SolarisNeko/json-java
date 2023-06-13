package com.neko233.json.convert;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public class PrettyJsonConfig extends DefaultJsonConfig {


    public static final PrettyJsonConfig instance = new PrettyJsonConfig();

    @Override
    public boolean isPretty() {
        return true;
    }

}
