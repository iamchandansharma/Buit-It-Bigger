package com.example;

import java.util.Random;

public class JokeProvider {

    private String[] Jokes = {
            "How do functions break up? They stop calling each other!",
            "When is a function a bad investment? When there's no return",
            "When do two functions fight? When they have arguments",
            "When do two functions fight? When they have arguments",
            "What do threads do after they make love? They go to sleep"
    };
    private Random mRandom = new Random();

    public String JokeTeller(){
        return Jokes[mRandom.nextInt(Jokes.length)];
    }
}
