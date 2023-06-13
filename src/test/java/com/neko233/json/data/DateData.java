package com.neko233.json.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author SolarisNeko
 * Date on 2023-06-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateData {


    Date date;

    LocalDateTime localDateTime;
    LocalDate localDate;
    LocalTime localTime;
    LocalTime nullData;
}
