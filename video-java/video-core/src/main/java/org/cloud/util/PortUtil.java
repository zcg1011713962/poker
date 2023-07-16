package org.cloud.util;


import org.cloud.entity.exception.FutureException;

import java.net.ServerSocket;
import java.util.LinkedHashSet;
import java.util.Optional;

public class PortUtil {
    private static LinkedHashSet<Integer> rtpPorts = new LinkedHashSet();
    private static LinkedHashSet<Integer> rtcpPorts = new LinkedHashSet();

    static {
        init();
    }

    private static void init() {
        for (int i = 18000; i < 20000; i++) {
            if(i % 2 == 0){
                rtpPorts.add(i);
            }else{
                rtcpPorts.add(i);
            }
        }
    }

    /**
     * 获取端口
     * rtp 端口偶数
     * rtcp 端口奇数
     * @param rtp
     * @return
     */
    public static Integer offerPort(boolean rtp){
        if(rtp){
            return getRtp();
        }
        return getRtcp();
    }

    private static Integer getRtp(){
        synchronized(PortUtil.rtpPorts) {
            Optional<Integer> opt = rtpPorts.stream().findFirst();
            if(opt.isPresent()){
                Integer port = opt.get();
                if(checkPort(port) && rtpPorts.remove(port))  return port;
                throw new FutureException("端口被占用或RTP端口移除失败");
            }
        }
        throw new FutureException("没用可用的RTP端口");
    }

    private static Integer getRtcp(){
        synchronized(PortUtil.rtcpPorts) {
            Optional<Integer> opt = rtcpPorts.stream().findFirst();
            if(opt.isPresent()){
                Integer port = opt.get();
                if(checkPort(port) && rtcpPorts.remove(port)) return port;
                throw new FutureException("端口被占用或Rtcp端口移除失败");
            }
        }
        throw new FutureException("没用可用的Rtcp端口");
    }

    /**
     * 放入端口
     * @param port
     * @return
     */
    public static boolean addPort(int port){
        if(port % 2 == 0){
            synchronized(PortUtil.rtpPorts) {
                return rtpPorts.add(port);
            }
        }else{
            synchronized(PortUtil.rtcpPorts) {
                return rtcpPorts.add(port);
            }
        }
    }

    /**
     * 检查端口
     * @param port
     * @return
     */
    private static boolean checkPort(int port){
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
            return true;
        }catch (Exception e){
            // todo 当前端口占用自动获取可用端口
            return false;
        }
    }

}
