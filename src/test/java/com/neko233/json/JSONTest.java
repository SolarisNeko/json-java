package com.neko233.json;

import com.neko233.json.data.TestSpeedPerson;
import com.neko233.json.data.SetElement;
import com.neko233.json.data.SubAction;
import com.neko233.json.enumData.EnumProperty;
import com.neko233.json.enumData.EnumUser;
import com.neko233.json.parameterizedData.ParameterizedUser;
import com.neko233.json.typeRef.JsonTypeRef;
import com.neko233.json.utils.ListUtilsForJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-06-02
 */
public class JSONTest {

    TestSpeedPerson testSpeedPerson;

    {
        HashSet<SetElement> setElementSet = new HashSet<>();
        setElementSet.add(SetElement.builder()
                .money(100.01)
                .build());

        testSpeedPerson = TestSpeedPerson.builder()
                .name("neko233")
                .age(18)
                .subActionList(ListUtilsForJson.of(
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
    public void serialize_pretty() {

        String json = JSON.serializePretty(testSpeedPerson);
        System.out.println(json);

        Assert.assertEquals(
                "{\n" +
                        "\t\"name\": \"neko233\",\n" +
                        "\t\"age\": 18,\n" +
                        "\t\"subActionList\": [\n" +
                        "\t\t{\n" +
                        "\t\t\t\"name\": \"a1\"\n" +
                        "\t\t},\n" +
                        "\t\t{\n" +
                        "\t\t\t\"name\": \"a2\"\n" +
                        "\t\t}\n" +
                        "\t],\n" +
                        "\t\"setElementSet\": [\n" +
                        "\t\t{\n" +
                        "\t\t\t\"money\": 100.01\n" +
                        "\t\t}\n" +
                        "\t]\n" +
                        "}",
                json
        );
    }


    @Test
    public void serialize() {

        String json = JSON.serialize(testSpeedPerson);
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
        String serialize = JSON.serialize(testSpeedPerson);
        TestSpeedPerson reTestSpeedPerson = JSON.deserialize(serialize, TestSpeedPerson.class);

        System.out.println("original = " + testSpeedPerson);
        System.out.println("deserialize = " + reTestSpeedPerson);

        Assert.assertTrue(reTestSpeedPerson.equals(testSpeedPerson));
    }

    @Test
    public void serialize_Array() {
        List<String> of = ListUtilsForJson.of("a", "b");
        String serialize = JSON.serialize(of);

        Assert.assertEquals("[\"a\",\"b\"]", serialize);
    }

    @Test
    public void deserialize_Array() throws Exception {
        String json = "[\"a\",\"b\"]";

        List<String> deserialize = JSON.deserializeArray(json, String.class);
        Assert.assertEquals("a", deserialize.get(0));
    }

    @Test
    public void serialize_objArray() {
        List<EnumUser> of = ListUtilsForJson.of(
                EnumUser.builder()
                        .userId(1)
                        .enumField(EnumProperty.A)
                        .build(),
                EnumUser.builder()
                        .userId(2)
                        .enumField(EnumProperty.B)
                        .build()
        );
        String serialize = JSON.serialize(of);

        Assert.assertEquals("[{\"userId\":1,\"enumField\":0},{\"userId\":2,\"enumField\":1}]", serialize);
    }

    @Test
    public void deserialize_objArray() throws Exception {
        String json = "[{\"userId\":1,\"enumField\":\"A\"},{\"userId\":2,\"enumField\":\"B\"}]";

        List<EnumUser> deserialize = JSON.deserializeArray(json, EnumUser.class);
        Assert.assertEquals(1, deserialize.get(0).getUserId());
        Assert.assertEquals(EnumProperty.A, deserialize.get(0).getEnumField());

        Assert.assertEquals(2, deserialize.get(1).getUserId());
        Assert.assertEquals(EnumProperty.B, deserialize.get(1).getEnumField());
    }

    /**
     * 范型
     */
    @Test
    public void serialize_parameterizedObj() {
        List<ParameterizedUser> of = ListUtilsForJson.of(
                ParameterizedUser.<String>builder()
                        .userId(1)
                        .data("ok")
                        .build()
        );
        String serialize = JSON.serialize(of);

        Assert.assertEquals("[{\"userId\":1,\"data\":\"ok\"}]", serialize);
    }

    @Test
    public void deserialize_parameterizedObj() throws Exception {
        String json = "[{\"userId\":1,\"data\":\"ok\"}]";


        // 范型保留
        JsonTypeRef<ParameterizedUser<String>> jsonTypeRef = new JsonTypeRef<ParameterizedUser<String>>() {
        };
        ParameterizedUser<String> deserialize = JSON.deserialize(json, jsonTypeRef);

        Assert.assertEquals(1, deserialize.getUserId());
        Assert.assertEquals("ok", deserialize.getData());
    }

    @Test
    public void serialize_enum() {
        EnumUser enumUser = new EnumUser();
        enumUser.setEnumField(EnumProperty.B);
        enumUser.setUserId(1);


        String serialize = JSON.serialize(enumUser);
        Assert.assertEquals("{\"userId\":1,\"enumField\":1}", serialize);
    }

    @Test
    public void deserialize_enum() throws Exception {
        String json = "{\"userId\":1,\"enumField\":1}";
        EnumUser deserialize = JSON.deserialize(json, EnumUser.class);

        Assert.assertEquals(1, deserialize.getUserId());
        Assert.assertEquals(EnumProperty.B, deserialize.getEnumField());
    }
}