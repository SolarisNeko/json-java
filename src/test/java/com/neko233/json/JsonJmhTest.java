package com.neko233.json;


import com.neko233.json.data.SetElement;
import com.neko233.json.data.SubAction;
import com.neko233.json.data.TestSpeedPerson;
import com.neko233.json.utils.ListUtilsForJson;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-06-12
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonJmhTest {

    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(JsonJmhTest.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("jmh-result-by-ms.json")
                .build())
                .run();
    }

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
    @Benchmark
    public void test_array_speed_just_serialize() {
        JSON.serialize(testSpeedPersonList);
    }

    /**
     * [serialize + deserialize ]
     * 3w = /s
     */
    @Benchmark
    public void test_jsonArray_speed_serialize_and_deserialize() throws Exception {
        String serialize = JSON.serialize(testSpeedPersonList);
        List<TestSpeedPerson> reTestSpeedPersonData = JSON.deserializeArray(serialize, TestSpeedPerson.class);

    }

    /**
     * [serialize + deserialize ]
     * 9w = /s
     */
    @Benchmark
    public void test_singleObject_speed_serialize_and_deserialize() throws Exception {
        String serialize = JSON.serialize(testSpeedPerson);
        TestSpeedPerson reTestSpeedPerson = JSON.deserialize(serialize, TestSpeedPerson.class);

    }

    /**
     * 93w / s
     */
    @Benchmark
    public void test_singleObject_speed_serialize() throws Exception {

        String serialize = JSON.serialize(testSpeedPerson);

    }
}

