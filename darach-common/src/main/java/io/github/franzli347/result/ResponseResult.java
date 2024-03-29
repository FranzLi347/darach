package io.github.franzli347.result;


import io.github.franzli347.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    Integer code;
    String msg;
    T data;

    public static <E> ResponseResult<E> success(String msg, E data){
        return new ResponseResult<E>(ErrorCode.SUCCESS.getCode(),msg,data);
    }

    public static <E> ResponseResult<E> success(E data){
        return new ResponseResult<E>(ErrorCode.SUCCESS.getCode(),"",data);
    }

    public static <E> ResponseResult<E> success(){
        return new ResponseResult<E>(ErrorCode.SUCCESS.getCode(),"",null);
    }

    public static <E> ResponseResult<E> error(ErrorCode errorCode){
        return new ResponseResult<E>(errorCode.getCode(),errorCode.getMessage(),null);
    }

    public static <E> ResponseResult<E> error(ErrorCode errorCode,String msg){
        return new ResponseResult<E>(errorCode.getCode(),msg,null);
    }

    public static <E> ResponseResult<E> error(String msg){
        return new ResponseResult<E>(ErrorCode.SYSTEM_ERROR.getCode(),msg,null);
    }

    public <E> ResponseResult<E> error(){
        return new ResponseResult<E>(ErrorCode.SYSTEM_ERROR.getCode(),ErrorCode.SYSTEM_ERROR.getMessage(),null);
    }

}
