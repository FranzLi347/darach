package io.github.franzli347.utils;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import io.github.franzli347.entity.Task;

public class ResponseTaskUtil {

    private ResponseTaskUtil() {}

    public static void responseTask(Task task) {
         HttpRequest.post("http://localhost:8080/video/responseTask/" + task.getId())
        .body(JSONUtil.toJsonStr(task))
        .execute()
        .body();
    }

}
