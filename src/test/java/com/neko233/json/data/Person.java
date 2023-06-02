
package com.neko233.json.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private String name;
    private int age;
    private List<SubAction> subActionList;
    private Set<SetElement> setElementSet;

}