package org.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cloud.entity.common.BaseResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpenAIResponse extends BaseResponse {
    private static final long serialVersionUID = -9165975143171890506L;
    protected String id;
    protected String object;
    protected int created;
    protected String model;
}
