package cn.meebox;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private android.widget.EditText login_username;
	private android.widget.EditText login_password;
	private Button btnLogin;
	private Button btnRegist;
	private Button btnForgot;
	
	private String username;
	private String password;

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
		
		login_username = (EditText)findViewById(R.id.etUsername);
		login_password = (EditText)findViewById(R.id.etPassword);
		
		
		btnLogin = (Button)findViewById(R.id.btnLogin);//登录按钮  
		btnLogin.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {
            	
            	username = login_username.getText().toString();
        		password = login_password.getText().toString();
        		
            	//Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
            	if((username.length()<4 || password.length()<4)){
            		//Toast.makeText(getApplicationContext(), "Input Error, Please input again.", Toast.LENGTH_SHORT).show();
            		login_username.setFocusable(true);
            		Toast.makeText(getApplicationContext(), "Input Error, Please input your username and password again.", Toast.LENGTH_SHORT).show();
            	}else{
            		MyThread m = new MyThread();
                	new Thread(m).start();
            	}
            }    
        }); 
		
		btnRegist = (Button)findViewById(R.id.btnRegist);//注册按钮  
		btnRegist.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	Intent i = new Intent(Loading.this, RegistActivity.class);
            	startActivity(i);
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
        	String authentication = "";
        	int uid = 0;
        	String sid = "";
        	
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            // 此处可以更新UI
            
            Bundle b = msg.getData();
            
            int httpcode = b.getInt("httpcode");
            String json = b.getString("json");
            
            try {
				JSONObject jsonObj = new JSONObject(json);
				authentication = jsonObj.getString("authentication").trim();
				sid = jsonObj.getString("sid").trim();
				uid = jsonObj.getInt("uid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            //Toast.makeText(getApplicationContext(),  authentication , Toast.LENGTH_LONG).show();
    	    
            //Log.d("response", json);
            
            //Toast.makeText(getApplicationContext(),  sid, Toast.LENGTH_SHORT).show();
            if(authentication.equals("successfully")){
            	//Toast.makeText(getApplicationContext(),  "login successfully.", Toast.LENGTH_SHORT).show();
            	//Toast.makeText(getApplicationContext(),  "Entering main activity...", Toast.LENGTH_SHORT).show();
            	Intent i = new Intent(Loading.this, MainActivity.class);
            	i.putExtra("username", uid);
            	i.putExtra("sid", sid);
            	startActivity(i);
            	
            }else{
            	Toast.makeText(getApplicationContext(),  "Login Failed. Please check your account", Toast.LENGTH_SHORT).show();
            }
            /*
            Loading.this.login_username.setText(color);
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
			
			String uri = "http://meebox.cn/login";
			
			//HttpClient httpClient = new DefaultHttpClient(); 
			
			DefaultHttpClient httpClient = new DefaultHttpClient();   
			//CloseableHttpClient httpclient = HttpClients.createDefault();
			
	        HttpGet httpget = new HttpGet(uri);
	        
	        HttpPost httppost = new HttpPost(uri);   
	        try {
				StringEntity reqEntity = new StringEntity("username=" + username +"&password=" + password);
				reqEntity.setContentType("application/x-www-form-urlencoded");   
		        // 设置请求的数据   
		        httppost.setEntity(reqEntity);   
		        // 执行   
		        HttpResponse response = httpClient.execute(httppost);  
		        
		        int httpcode = response.getStatusLine().getStatusCode();
		        
		        String json = null;
		        
		        HttpEntity entity = response.getEntity(); 
		        
		        InputStream in = entity.getContent();  
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        StringBuffer sb = new StringBuffer(); 
		        String line;
		        while ((line = br.readLine()) != null) {
		        	sb.append(line);              
		        }
		        
		        json = sb.toString();
		        
		        b.putInt("httpcode", httpcode);
		        b.putString("json", json);
		        
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
			Loading.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}
}
