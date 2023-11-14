package com.api.watchshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDataResponse {
    private long id;
    private String name;
    private String type;
    private String filePath;
    private LocalDateTime dateCreated;
}
