# DateTime233 | Simplify Your Date and Time Operations

![DateTime233](./json-java-logo-v1.png)



# Slogan
Tired of dealing with the limitations of LocalDateTime and Date objects? DateTime233 provides extensive functionality and eliminates the need for excessive customizations.

厌倦了局限性强的 LocalDateTime 和 Date 对象？DateTime233 提供了丰富的功能，无需进行繁琐的自定义。

## Introduction
DateTime233 is a powerful and intuitive DateTime utility that simplifies working with dates and times. Designed from scratch with a flux-style architecture, it seamlessly integrates with the DateTime233 API.

DateTime233 是一个强大且直观的日期和时间工具，简化了日期和时间操作。从零开始设计的，采用流式架构，与 DateTime233 API 无缝衔接。

从 0 开始设计的 DateTime233, 提供可并发的一体化 DateTime 操作. 

# Easy Integration
## maven
```xml
<dependency>
    <groupId>com.neko233</groupId>
    <artifactId>json-java</artifactId>
    <version>0.0.1</version>
</dependency>
```
## gradle
```kotlin
implementation("com.neko233:json-java:0.0.1")

```

## JDK Compatibility | JDK 版本支持
Latest supported versions:

JDK 8 = 0.0.1

JDK 11 = 0.0.1

JDK 17 = 0.0.1

## Key Terminology | 术语

To make the most of DateTime233, familiarize yourself with the following terms and concepts:

1. originTimeMs: Milliseconds since January 1, 1970, representing the original time. 
2. zoneTimeMs: Milliseconds in the current time zone since January 1, 1970. 
3. DateTime: A date and time represented in the format "yyyy-MM-dd HH:mm:ss."
4. Period: A time interval specified by a start and end timestamp. 
5. refreshMs: The time interval between period refreshes, typically measured in milliseconds.

---

1. originTimeMs = millis second = 毫秒, 从 1970-01-01 00:00:00 至今 
2. zoneTimeMs = zone time ms = 时区下的毫秒, 从 1970-01-01 00:00:00 至今 
3. DateTime = yyyy-MM-dd HH:mm:ss 组成的日期时间 
4. Period = 周期 = [start, endMs] -> {startMs, endMs, expireMs, refreshMs} 
5. RefreshMs = period refresh by refreshMs / time, like 100ms refresh , in 1 s have 10 refresh count.


# Seamless Integration

DateTime233 seamlessly connects with LocalDateTime and Date objects, eliminating the need for extensive custom packaging.

DateTime233 可无缝连接 LocalDateTime 和 Date 对象，省去了繁琐的自定义封装。

# License

DateTime233 is licensed under Apache 2.0.

## Download

### Maven

```xml

<dependency>
    <groupId>com.neko233</groupId>
    <artifactId>json-java</artifactId>
    <version>0.0.1</version>
</dependency>

```

### Gradle

```groovy
implementation group: 'com.neko233', name: 'json-java', version: '0.0.1'
```

# API
String json = JSON.serialize(object)

T obj = JSON.deserialize(text, Class<T> clazz)

# Code 
```java
    @Test
    public void testDeserialize() throws Exception {
        String serialize = JSON.serialize(person);
        
        Person rePerson = JSON.deserialize(serialize, Person.class);

        System.out.println("original = " + person);
        System.out.println("deserialize = " + rePerson);

        // isSame
        Assert.assertTrue(rePerson.equals(person));
    }

```