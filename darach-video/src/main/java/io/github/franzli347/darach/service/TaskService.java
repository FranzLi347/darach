package io.github.franzli347.darach.service;

import io.github.franzli347.model.entity.Task;

public interface TaskService {
    void triggerCoverTask(Task task);

    Boolean responseTask(Integer id, Task task);
}
