package cn.meebox;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.meebox.util.SystemUiHider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Loading extends Activity {
	private EditText login_username;
	private EditText login_password;
	private Button btnLogin;

	MyHandler myHandler;
	
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		myHandler = new MyHandler();
		
		btnLogin = (Button)findViewById(R.id.btnLogin);//获取按钮资源    
		btnLogin.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyThread m = new MyThread();
            	new Thread(m).start();
            }    
        });  
	}
	
	class MyHandler extends Handler {
		public MyHandler(){ }
 
		public MyHandler(Looper L) {
            super(L);
        }
		
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            // 此处可以更新UI
            
            Bundle b = msg.getData();
            
            int httpcode = b.getInt("httpcode");
            
            if(httpcode == 200){
            	Intent i = new Intent(Loading.this, MainActivity.class);
            	startActivity(i);
            }
            /*
            Loading.this.login_username.setText(color);
            */
        }
    }

	class MyThread implements Runnable {
		public void run() {
			
			Log.d("thread:", "myThread");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			
			String uri = "http://www.baidu.com/";
			
			HttpClient httpClient = new DefaultHttpClient(); 
			
			//CloseableHttpClient httpclient = HttpClients.createDefault();
			
	        HttpGet httpget = new HttpGet(uri);
	        
	        HttpResponse response;
			try {
				response = httpClient.execute(httpget);
				b.putInt("httpcode", response.getStatusLine().getStatusCode());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			msg.setData(b);
			Loading.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}
}
