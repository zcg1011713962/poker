package org.cloud.netty.service;


import org.cloud.entity.common.BaseResponse;

import java.util.concurrent.CompletableFuture;

public interface RtspService<T> {

    CompletableFuture<BaseResponse> connect(T t);

    CompletableFuture<BaseResponse> disconnect(T t);

}
