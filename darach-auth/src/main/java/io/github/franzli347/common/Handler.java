package io.github.franzli347.common;

public interface Handler{
    void setNext(Handler handler);
    void handle(Object obj,Boolean preventNext);
}
