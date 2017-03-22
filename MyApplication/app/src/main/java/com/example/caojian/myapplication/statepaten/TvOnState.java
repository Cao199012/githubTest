package com.example.caojian.myapplication.statepaten;

/**
 * Created by caojian on 16/9/5.
 */
public class TvOnState implements Test {
    @Override
    public void nextChannel() {
        System.out.print("下一个频道");
    }

    @Override
    public void preChannel() {
        System.out.print("上一个频道");
    }

    @Override
    public void turnOn() {
       System.out.print("音量变大");
    }

    @Override
    public void turnOff() {
        System.out.print("音量变小");
    }
}
