pickle
===========

**Description:**
    Java library for pickling (serialize) / unpickling (deserialize) data as
    the Python's Pickle module.

**Serializable class example:**
```java
package org.java.serializer.pickle.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = false, chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
}
```

**Testing pickle:**
```java
package org.java.serializer.pickle.test;

import java.util.ArrayList;
import java.util.List;

import org.java.serializer.pickle.Pickle;
import org.java.serializer.pickle.models.Person;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PickleTest {
    
    private static List<Person> people = new ArrayList<>();
    private static List<String> picklingPeople = new ArrayList<>();
    private static List<Person> unpicklingPeople = new ArrayList<>();
    
    @BeforeClass
    public static void setUp() throws Exception {
        people.add(new Person("Pedro", 33));
        people.add(new Person("João", 29));
        people.add(new Person("Maria", 65));
        people.add(new Person("Joaquina", 60));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        people.clear();
    }
    
    @Test
    public void test() {
        String message = ">>>> PROCESSO DE SERIALIZAÇÃO/DESERIALIZAÇÃO ENTRE CODIFICAÇÃO BASE 64 E OBJECT <<<<\n";
        System.out.println(message);
        people.forEach(pessoa -> {
            picklingPeople.add(Pickle.dumps(pessoa));
        });
        message = "---- Objetos da classe Pessoa codificadas em base 64 ----\n";
        System.out.println(message);
        picklingPeople.forEach(System.out::println);
        picklingPeople.forEach(ps -> {
            unpicklingPeople.add((Person) Pickle.loads(ps));
        });
        message = "\n---- Strings na base 64 decodificadas para objetos da classe Pessoa ----\n";
        System.out.println(message);
        unpicklingPeople.forEach(System.out::println);
        message = "Detectada [FALHA] no processo de serialização/deserialização.";
        Assert.assertArrayEquals(message, people.toArray(), unpicklingPeople.toArray());
    }
    
}
```

**How to compile?**
```java
    javac -cp .:src:lib/* -d bin src/*.java
```

**How to run?**
```java
    java -cp .:bin:lib/* PickleApp
```

**Note:**

    1. Don't forget to make your classes implements java.io.Serializable.
    
	2. This is a simple project however I believe that it can be helpful 
	   	to someone in somewhere.
