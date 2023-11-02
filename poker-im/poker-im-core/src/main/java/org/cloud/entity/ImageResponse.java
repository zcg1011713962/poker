package org.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageResponse extends OpenAIResponse{

    private Datas [] data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Datas{
        private String url;
    }
}
