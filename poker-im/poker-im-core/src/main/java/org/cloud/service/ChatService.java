package org.cloud.service;

import org.cloud.entity.ChatRequest;
import org.cloud.entity.ImageRequest;

import java.util.concurrent.CompletableFuture;

public interface ChatService<T> {
    /**
     * 创建聊天完成
     * @return
     */
    CompletableFuture<T> completions(ChatRequest chatRequest);

    /**
     * 列出型号
     * @return
     */
    CompletableFuture<T> models();

    /**
     * 创建图像
     * @return
     */
    CompletableFuture<T> createImages(ImageRequest imageRequest);

    /**
     * 创建图像编辑
     * @return
     */
    CompletableFuture<T> imagesEdits();
}
