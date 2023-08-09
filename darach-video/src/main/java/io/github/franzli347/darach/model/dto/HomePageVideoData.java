package io.github.franzli347.darach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageVideoData {

    String displayName;

    String videoUrl;

    String targetRedirectUrl;
}
