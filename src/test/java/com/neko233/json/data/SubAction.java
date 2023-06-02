package com.neko233.json.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubAction {

    private String name;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubAction)) {
            return false;
        }
        SubAction subAction = (SubAction) o;
        return Objects.equals(this.name, subAction.name);
    }

}
