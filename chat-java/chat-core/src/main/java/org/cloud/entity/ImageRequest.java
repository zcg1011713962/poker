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
public class ImageRequest extends BaseRequest {
    // 描述
    private String prompt;
    // 生成图片n张
    private int n;
    // 图片尺寸 '256x256', '512x512', '1024x1024'
    private String size = "512x512";


}
