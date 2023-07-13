package io.github.franzli347.darach.common;


/**
 * @Author Franz
 * 抽象处理器
 */
public abstract class AbstractHandler implements Handler {
    protected Handler nextHandler;

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(Object obj,Boolean preventNext) {
        if (nextHandler == null || preventNext){
            return;
        }
        nextHandler.handle(obj,false);
    }
}
