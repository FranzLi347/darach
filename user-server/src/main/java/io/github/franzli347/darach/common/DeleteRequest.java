package io.github.franzli347.darach.common;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Franz
 * 删除请求
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}