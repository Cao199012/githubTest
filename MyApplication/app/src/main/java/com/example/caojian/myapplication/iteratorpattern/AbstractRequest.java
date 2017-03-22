package com.example.caojian.myapplication.iteratorpattern;

import java.util.Objects;

/**
 * Created by caojian on 16/9/18.
 */
public abstract class AbstractRequest {
    protected AbstractRequest mRequest;
    public AbstractRequest(Object object){
        mRequest = (AbstractRequest) object;
    }

    public abstract AbstractRequest getmRequest();
    /*
    * 获取处理级别
    * */
    public abstract int getLevel();
}
