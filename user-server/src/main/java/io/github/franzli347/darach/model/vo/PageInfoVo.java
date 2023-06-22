package io.github.franzli347.darach.model.vo;

import lombok.Data;

@Data
public class PageInfoVo<T> {

    long page;

    long size;

    long total;

    T records;
}
