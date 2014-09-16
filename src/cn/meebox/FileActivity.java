package cn.meebox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.meebox.Loading.MyThread;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.oauth.BaiduOAuth;
import com.baidu.oauth.BaiduOAuth.BaiduOAuthResponse;
import com.baidu.oauth.BaiduOAuth.OAuthListener;

public class FileActivity extends Activity {
	private int uid;
	String sid;
	
	MyHandler myHandler;
	
	private final String mbApiKey = "IGTilpTos6IgaUEZYc3wBkGK";
	private Button getAccessToken;//添加响应按钮
	private Button getList;//添加响应按钮
	
	String BaiDuAccessToken = "";
	String BaiDuUsername = "";
	
	private Button mButton;
    private PopupWindow mPopupWindow;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
        Intent intent = getIntent();
        sid = intent.getExtras().getString("sid");
        Log.i("sid1", sid);
        myHandler = new MyHandler();
        
        MyThread m = new MyThread();
    	new Thread(m).start();
        
    	getAccessToken = (Button)findViewById(R.id.getAccessToken);//响应按钮类

    	getAccessToken.setOnClickListener(new Button.OnClickListener(){//创建监听    
    		@Override
            public void onClick(View v) {
    			BaiduOAuth oauthClient = new BaiduOAuth();
    			oauthClient.startOAuth(FileActivity.this, mbApiKey, new String[]{"basic"},new BaiduOAuth.OAuthListener() {
                    @Override
                    public void onException(String msg) {
                        Toast.makeText(getApplicationContext(), "Login failed " + msg, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete(BaiduOAuthResponse response) {
                        if(null != response){
                            BaiDuAccessToken = response.getAccessToken();
                            BaiDuUsername = response.getUserName();
                            
                            
                            SharedPreferences.Editor meebox = getSharedPreferences("meebox", 0).edit();  
                            meebox.putString("BaiDuAccessToken",BaiDuAccessToken);  
                            meebox.putString("BaiDuUsername",BaiDuUsername);  
                            meebox.commit();
                            
                            BaiduGetListThread m = new BaiduGetListThread();
                        	new Thread(m).start();
                            //Toast.makeText(getApplicationContext(), "Token: " + BaiDuaccessToken + "    User name:" + response.getUserName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }   
        });
    	
    	getList = (Button)findViewById(R.id.getList);//响应按钮类

    	getList.setOnClickListener(new Button.OnClickListener(){//创建监听    
    		@Override
            public void onClick(View v) {

                SharedPreferences meebox = getSharedPreferences("meebox", 0);  
                String BaiDuList = meebox.getString("BaiDuList", null);  
                String BaiDuAccessToken = meebox.getString("BaiDuAccessToken", null);  
                String uri = meebox.getString("uri", null);  
                Toast.makeText(getApplicationContext(), BaiDuAccessToken, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), uri, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), BaiDuList, Toast.LENGTH_SHORT).show();
    		}
    	});
    	
    	
    	
    	Intent i = new Intent(FileActivity.this, TestActivity.class);
    	startActivity(i);
	}
	
	
	class MyHandler extends Handler {
		public MyHandler(){ }
 
		public MyHandler(Looper L) {
            super(L);
        }
		
        @Override
        public void handleMessage(Message msg) {
        	super.handleMessage(msg);
            Bundle b = msg.getData();
            String url = b.getString("url");
            int httpcode = b.getInt("httpcode");
            
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            
            
            JSONArray files = new JSONArray();  
		    JSONObject response;
			try {
				response = new JSONObject(url);
				files = (JSONArray)response.get("file");
				
				for(int i=0; i<files.length(); i++){
					String f = files.getJSONObject(i).get("filename").toString();
					
					Toast.makeText(getApplicationContext(), f, Toast.LENGTH_LONG).show();
				}
				
				
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }

	
	
	
	class MyThread implements Runnable {
		public void run() {
			Log.d("thread:", "myThread");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			
			String uri = "http://meebox.cn/file";
			
			//HttpClient httpClient = new DefaultHttpClient(); 
			
			DefaultHttpClient httpClient = new DefaultHttpClient();   
			//CloseableHttpClient httpclient = HttpClients.createDefault();
		
	        HttpPost httppost = new HttpPost(uri);
	        
	        httppost.addHeader("Cookie", "JSESSIONID=" + sid);
	        try {

	        	HttpResponse response = httpClient.execute(httppost);
	        	
		        HttpEntity entity = response.getEntity(); 
		        
		        InputStream in = entity.getContent();  
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        String json = "";
		        String line;
		        while ((line = br.readLine()) != null) {
		        	json += line;
		        }
		        
		        b.putString("url",json);
		        b.putInt("httpcode",response.getStatusLine().getStatusCode());
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
			FileActivity.this.myHandler.sendMessage(msg);
		}
	}
	
	
	class BaiduGetListThread implements Runnable {
		public void run() {
			Log.d("thread:", "BaiduThread");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			
			SharedPreferences meebox = getSharedPreferences("meebox", 0);  
            String BaiDuAccessToken = meebox.getString("BaiDuAccessToken", null);  
             
			String uri = "https://pcs.baidu.com/rest/2.0/pcs/quota?method=info&access_token=" + BaiDuAccessToken;
			
			//HttpClient httpClient = new DefaultHttpClient(); 
			
			DefaultHttpClient httpClient = new DefaultHttpClient();   
			//CloseableHttpClient httpclient = HttpClients.createDefault();
		
	        HttpGet httpget = new HttpGet(uri);
	        
	        try {

	        	HttpResponse response = httpClient.execute(httpget);
	        	
		        HttpEntity entity = response.getEntity(); 
		        
		        InputStream in = entity.getContent();  
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        String json = "";
		        String line;
		        while ((line = br.readLine()) != null) {
		        	json += line;
		        }
		        
		        b.putString("url",json);
		        b.putInt("httpcode",response.getStatusLine().getStatusCode());
		        
		        
		        SharedPreferences.Editor meeboxEditor = getSharedPreferences("meebox", 0).edit();  
		        meeboxEditor.putString("BaiDuList",json);  
		        meeboxEditor.putString("uri",uri);
		        meeboxEditor.commit();
	            
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

	        
		}
	}
	
	
}
