package org.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cloud.entity.common.BaseRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRequest extends BaseRequest {

    private String model;

    private Messages[] messages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Messages{
        private String role;
        private String content;
    }


}
