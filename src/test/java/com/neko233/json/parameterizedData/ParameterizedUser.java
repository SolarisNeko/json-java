package com.neko233.json.parameterizedData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-06-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterizedUser<T> {

    long userId;
    T data;

}
