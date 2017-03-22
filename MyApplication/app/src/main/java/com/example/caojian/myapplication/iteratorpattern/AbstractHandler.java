package com.example.caojian.myapplication.iteratorpattern;

/**
 * Created by caojian on 16/9/18.
 */
public abstract class AbstractHandler {
    protected AbstractHandler nextHandler;

//    nextHandler
    /*
    * 处理请求
    *
    * */
    public void handlerRequest(AbstractRequest pRequest){
        if(pRequest.getLevel() == getHandlerLevel()){
            handler(pRequest);
        }else if(nextHandler != null){
            nextHandler.handlerRequest(pRequest);
        }

    }
    public abstract void handler(AbstractRequest pRequest);

    public abstract int getHandlerLevel();
}
