package io.github.franzli347.common;

import io.github.franzli347.model.entity.UserEntity;

public class ThreadLocalUserHolder implements AutoCloseable{
    static final ThreadLocal<UserEntity> ctx = new ThreadLocal<>();

    public ThreadLocalUserHolder(UserEntity userEntity) {
        ctx.set(userEntity);
    }

    public static UserEntity currentUser() {
        return ctx.get();
    }

    @Override
    public void close() throws Exception {
        ctx.remove();
    }
}
