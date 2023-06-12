package com.neko233.json.enumData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2023-06-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnumUser {

    long userId;
    EnumProperty enumField;

}
