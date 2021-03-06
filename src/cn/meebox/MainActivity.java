package cn.meebox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private int uid;
	private String sid;
	
	MyHandler myHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent intent = getIntent();
        
		uid = intent.getExtras().getInt("uid");
        sid = intent.getExtras().getString("sid");
        
        myHandler = new MyHandler();
        
        MyThread m = new MyThread();
    	new Thread(m).start();
        
	}
	
	
	class MyHandler extends Handler {
		public MyHandler(){ }
 
		public MyHandler(Looper L) {
            super(L);
        }
		
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
        	/*
        	String authentication = "";
        	int uid = 0;
        	String sid = "";
        	
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String url = b.getString("url");
            //Toast.makeText(getApplicationContext(),  authentication , Toast.LENGTH_LONG).show();
    	    
            //ImageView iv = (ImageView)findViewById(R.id.ImageView03);//登录按钮  
            //TextView tv = (TextView)findViewById(R.id.textView1);//登录按钮
            //tv.setText(url);
             * 
             * 
             */
            
        }
    }

	
	class MyThread implements Runnable {
		public void run() {
			/*
			String password = null;
			try {
				MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
				digest.update(password.getBytes());
				byte messageDigest[] = digest.digest();
				StringBuffer hexString = new StringBuffer();
				// 字节数组转换为 十六进制 数
				for (int i = 0; i < messageDigest.length; i++) {
					String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
					if (shaHex.length() < 2) {
						hexString.append(0);
					}
					hexString.append(shaHex);
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			*/
			
			Log.d("thread:", "myThread");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			
			String uri = "http://meebox.cn/file";
			
			//HttpClient httpClient = new DefaultHttpClient(); 
			
			DefaultHttpClient httpClient = new DefaultHttpClient();   
			//CloseableHttpClient httpclient = HttpClients.createDefault();
		
			
	        HttpGet httpget = new HttpGet(uri);
	        
	        Toast.makeText(getApplicationContext(), sid, Toast.LENGTH_LONG);
	        httpget.setHeader("Cookie", "JSESSIONID="+sid);
	        
	        try {

	        	HttpResponse response = httpClient.execute(httpget);
	        	
		        HttpEntity entity = response.getEntity(); 
		        
		        InputStream in = entity.getContent();  
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        StringBuffer sb = new StringBuffer(); 
		        String line;
		        while ((line = br.readLine()) != null) {
		        	sb.append(line);              
		        }
		        
		        b.putString("url", sb.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
			msg.setData(b);
			MainActivity.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}
}
