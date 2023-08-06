package io.github.franzli347.darach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoverTaskCreateDto {

    private String fileName;

    private Map<String, Object> taskParam;

}
