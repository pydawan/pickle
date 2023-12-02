package org.java.serializer.pickle.test;

import com.github.javafaker.Faker;
import org.java.serializer.pickle.Pickle;
import org.java.serializer.pickle.models.Person;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class PickleTest {
    
    private static final List<Person> people = new ArrayList<>();
    private static final List<String> picklingPeople = new ArrayList<>();
    private static final List<Person> unpicklingPeople = new ArrayList<>();
    private static final Locale localeForBrazil = new Locale("pt-BR");
    private static final Faker faker = new Faker(localeForBrazil);
    
    @BeforeClass
    public static void setUp() throws Exception {
        people.add(new Person("Pedro", 33));
        people.add(new Person("João", 29));
        people.add(new Person("Maria", 65));
        people.add(new Person("Joaquina", 60));
        IntStream.rangeClosed(1, 100)
                .forEach(i -> people.add(
                        new Person(
                            faker.name().fullName(),
                            faker.number().numberBetween(1, 100)
                        )
                ));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        people.clear();
    }

//    @Ignore
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
            unpicklingPeople.add(Pickle.loads(ps, Person.class)); 
            // or unpicklingPeople.add((Person) Pickle.loads(ps));
        });
        message = "\n---- Strings na base 64 decodificadas para objetos da classe Pessoa ----\n";
        System.out.println(message);
        unpicklingPeople.forEach(System.out::println);
        message = "Detectada [FALHA] no processo de serialização/deserialização.";
        Assert.assertArrayEquals(message, people.toArray(), unpicklingPeople.toArray());
    }

}

