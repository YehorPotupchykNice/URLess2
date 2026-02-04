package org.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CreateURLResponse {
    private String url;
    private String originalUrl;
}
