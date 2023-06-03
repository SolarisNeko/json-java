# JSON


## Introduction
朴素的 JSON 序列化工具.


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

1. serialize: 序列化. JSON String -> Object 
2. deserialize : 反序列化. Object -> JSON String

# License

json-java is licensed under Apache 2.0.

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


# Code
## Demo 
```java
        String serialize = JSON.serialize(person);
        Person rePerson = JSON.deserialize(serialize, Person.class);

        System.out.println("original = " + person);
        System.out.println("deserialize = " + rePerson);

        Assert.assertTrue(rePerson.equals(person));

```