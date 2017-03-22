package com.example.caojian.myapplication.statepaten;

/**
 * Created by caojian on 16/9/5.
 */
public class TvController implements PowerContorller {
    private Test mTvState;

    private void setSate(Test pTest){
        mTvState = pTest;
    }
    @Override
    public void powerOn() {
        setSate(new TvOnState());
    }

    @Override
    public void powerOff() {
        setSate(new TvOffState());
    }
    

}
