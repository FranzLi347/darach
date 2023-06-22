package io.github.franzli347.servremanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Server {
  // 服务器基本信息
    private String ip;
    private String name;
    private String os;
    private String cpu;
    private String cpuFree;
    private String memory;
    private String memoryFree;
    private String disk;
    private String diskFree;
    private String network;
    private String updateTime;
}
