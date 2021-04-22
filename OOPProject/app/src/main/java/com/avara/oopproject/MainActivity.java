package com.avara.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.avara.oopproject.HouseDecor.room;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        User myUser = new User();
//        myUser.name = "Atil";
//        myUser.job  = "Instructor";
//
//        User newUser = new User();
//        newUser.name = "Kirk";
//        newUser.job  = "Musician";

        User myUser = new User("Atil", "Instructor");

        System.out.println(myUser.name);

        // Encapsulation

        Musician james = new Musician("James", "Guitar", 50);
        System.out.println(james.getName());

        // Inheritence
        SuperMusician lars = new SuperMusician("Lars","Drums",55);
        System.out.println(lars.getAge());

        // Polymorphism
        // Static Polymorphism
        Mathematics mathematics = new Mathematics();
        System.out.println(mathematics.sum());
        System.out.println(mathematics.sum(5,3));
        System.out.println(mathematics.sum(5,3,4));

        // Dynamic Polymorphism
        Animal myAnimal = new Animal();
        myAnimal.sing();

        Dog barley = new Dog();
        barley.test();
        barley.sing();

        User myUser2 = new User("Atil", "Instructor");
        System.out.println(myUser2.information());


        Piano myPiano = new Piano();
        myPiano.brand = "Yamaha";
        myPiano.digital= true;
        myPiano.info();


    }
}