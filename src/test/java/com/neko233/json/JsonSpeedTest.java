package com.neko233.json;

import com.neko233.json.data.SetElement;
import com.neko233.json.data.SubAction;
import com.neko233.json.data.TestSpeedPerson;
import com.neko233.json.utils.ListUtilsForJson;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-06-12
 */
public class JsonSpeedTest {

    TestSpeedPerson testSpeedPerson;
    List<TestSpeedPerson> testSpeedPersonList;

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

        testSpeedPersonList = ListUtilsForJson.of(
                TestSpeedPerson.builder()
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
                        .build(),
                TestSpeedPerson.builder()
                        .name("2")
                        .age(18)
                        .subActionList(ListUtilsForJson.of(
                                SubAction.builder()
                                        .name("a12")
                                        .build(),
                                SubAction.builder()
                                        .name("a23")
                                        .build()
                        ))
                        .setElementSet(setElementSet)
                        .build(),
                TestSpeedPerson.builder()
                        .name("1234")
                        .age(432531)
                        .subActionList(ListUtilsForJson.of(
                                SubAction.builder()
                                        .name("34")
                                        .build(),
                                SubAction.builder()
                                        .name("23")
                                        .build()
                        ))
                        .setElementSet(setElementSet)
                        .build()
        );
    }

    /**
     * [serialize ]
     * 32w = 3 object list/s
     */
    @Test
    public void test_array_speed_just_serialize() {

        for (int i = 0; i < 10000; i++) {
            JSON.serialize(testSpeedPersonList);
        }

        int count = 0;
        long startMs = System.currentTimeMillis();
        while (true) {
            JSON.serialize(testSpeedPersonList);
            count++;

            long endMs = System.currentTimeMillis();
            if (endMs - startMs > 1000) {
                break;
            }
        }

        // single thread , single object = 58w count / 3 object list = 32w count
        System.out.println("object List  serialize.  预热后, 1s 可执行 count = " + count);
    }

    /**
     * [serialize + deserialize ]
     * 3w = /s
     */
    @Test
    public void test_jsonArray_speed_serialize_and_deserialize() throws Exception {

        for (int i = 0; i < 10000; i++) {
            String serialize = JSON.serialize(testSpeedPersonList);
            List<TestSpeedPerson> reTestSpeedPersonData = JSON.deserializeArray(serialize, TestSpeedPerson.class);
        }

        int count = 0;
        long startMs = System.currentTimeMillis();
        while (true) {
            String serialize = JSON.serialize(testSpeedPersonList);
            List<TestSpeedPerson> reTestSpeedPersonData = JSON.deserializeArray(serialize, TestSpeedPerson.class);
            count++;

            long endMs = System.currentTimeMillis();
            if (endMs - startMs > 1000) {
                break;
            }
        }

        // single thread serialize then deserialize, speed count/s = 3w
        System.out.println("object List  serialize + deserialize. 预热后, 1s 可执行 count = " + count);
    }

    /**
     * [serialize + deserialize ]
     * 9w = /s
     */
    @Test
    public void test_singleObject_speed_serialize_and_deserialize() throws Exception {

        for (int i = 0; i < 10000; i++) {
            String serialize = JSON.serialize(testSpeedPerson);
            TestSpeedPerson reTestSpeedPerson = JSON.deserialize(serialize, TestSpeedPerson.class);
        }

        int count = 0;
        long startMs = System.currentTimeMillis();
        while (true) {
            String serialize = JSON.serialize(testSpeedPerson);
            TestSpeedPerson reTestSpeedPerson = JSON.deserialize(serialize, TestSpeedPerson.class);
            count++;

            long endMs = System.currentTimeMillis();
            if (endMs - startMs > 1000) {
                break;
            }
        }

        // single thread serialize then deserialize, speed count/s = 3w
        System.out.println("single object. 预热后, 1s 可执行 serialize + deserialize count = " + count);
    }

    /**
     * 93w / s
     */
    @Test
    public void test_singleObject_speed_serialize() throws Exception {

        for (int i = 0; i < 10000; i++) {
            String serialize = JSON.serialize(testSpeedPerson);
        }

        int count = 0;
        long startMs = System.currentTimeMillis();
        while (true) {
            String serialize = JSON.serialize(testSpeedPerson);
            count++;

            long endMs = System.currentTimeMillis();
            if (endMs - startMs > 1000) {
                break;
            }
        }

        // single thread serialize then deserialize, speed count/s = 3w
        System.out.println("预热后, 1s 可执行 serialize + deserialize count = " + count);
    }
}
