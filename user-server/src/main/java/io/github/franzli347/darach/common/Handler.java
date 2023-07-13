package io.github.franzli347.darach.common;

public interface Handler{
    void setNext(Handler handler);
    void handle(Object obj,Boolean preventNext);
}
