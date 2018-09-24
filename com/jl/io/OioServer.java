package com.jl.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class OioServer {
	
	public static void main(String[] args) {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		try {
			//创建socket服务，监听10101端口
			ServerSocket server  = new ServerSocket(10101);
			System.out.println("服务器启动");
			while(true){
				//获取一个套接字（阻塞）
				final Socket socket = server.accept();
				System.out.println("来了一个新客户端！");
				newCachedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						Handler(socket);
					}
				});
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void Handler(Socket socket) {
		try {
			byte[] bytes = new byte[1024];
			InputStream inputStream = socket.getInputStream();
			while(true){
				//读取数据（阻塞）
				int read = inputStream.read(bytes);
				if(read != -1){
					System.out.println(new String(bytes, 0,read));
				}else{
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("socket关闭！");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
}
