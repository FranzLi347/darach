package io.github.franzli347.darach.controller;

import io.github.franzli347.darach.service.TaskService;
import io.github.franzli347.model.entity.Task;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //todo replace url
    @PostMapping("/video/responseTask/{id}")
    public Boolean responseTask(@PathVariable Integer id, @RequestBody Task task) {
        //todo replace by rpc
        return taskService.responseTask(id,task);
    }

}
