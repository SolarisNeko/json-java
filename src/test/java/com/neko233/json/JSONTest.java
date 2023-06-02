package com.neko233.json;

import com.neko233.json.data.Person;
import com.neko233.json.data.SetElement;
import com.neko233.json.data.SubAction;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public class JSONTest {

    Person person;

    {
        HashSet<SetElement> setElementSet = new HashSet<>();
        setElementSet.add(SetElement.builder()
                .money(100.01)
                .build());

        person = Person.builder()
                .name("neko233")
                .age(18)
                .subActionList(ListUtils233.of(
                        SubAction.builder()
                                .name("a1")
                                .build(),
                        SubAction.builder()
                                .name("a2")
                                .build()
                ))
                .setElementSet(setElementSet)
                .build();
    }

    @Test
    public void serialize() {

        String json = JSON.serialize(person);
        System.out.println(json);

        Assert.assertEquals(
                "{\"name\":\"neko233\",\"age\":18,\"subActionList\":[{\"name\":\"a1\"},{\"name\":\"a2\"}],\"setElementSet\":[{\"money\":100.01}]}",
                json
        );

    }

    @Test
    public void serializeToBytes() {
    }

    @Test
    public void testSerializeToBytes() {
    }

    @Test
    public void deserialize() {
    }


    @Test
    public void testDeserialize() throws Exception {
        String serialize = JSON.serialize(person);
        Person rePerson = JSON.deserialize(serialize, Person.class);

        System.out.println("original = " + person);
        System.out.println("deserialize = " + rePerson);

        Assert.assertTrue(rePerson.equals(person));
    }

    @Test
    public void deserializeArray() {
    }

    @Test
    public void testDeserialize2() {
    }
}