package cn.meebox.android.baidu;

import android.content.SharedPreferences;
import android.widget.Toast;
import cn.meebox.FileActivity;

import com.baidu.oauth.BaiduOAuth;
import com.baidu.oauth.BaiduOAuth.BaiduOAuthResponse;

public class PCS {
	
	
	public PCS() {
		// TODO Auto-generated constructor stub
		
	}

	public void auth(){
		BaiduOAuth oauthClient = new BaiduOAuth();
		/*
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
            
		}
		*/
	}
	public String getQuota(){
		return "";
	}
}

