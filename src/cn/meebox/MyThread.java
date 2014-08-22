package cn.meebox;

import cn.meebox.*;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

class MyThread implements Runnable {
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("thread:", "myThread");
		Message msg = new Message();
		Bundle b = new Bundle();// 存放数据
		b.putString("color", "hello");
		msg.setData(b);
		//Loading.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
	}
}