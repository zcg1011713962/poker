package org.cloud.web;


import lombok.Data;
import org.cloud.entity.common.BaseRequest;

@Data
public class RtspRequest extends BaseRequest {
    private String url;

}