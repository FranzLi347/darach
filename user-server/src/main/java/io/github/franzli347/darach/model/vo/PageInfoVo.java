package io.github.franzli347.darach.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoVo<T> {

    long page;

    long size;

    long total;

    T records;
}
