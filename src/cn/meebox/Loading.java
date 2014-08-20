package cn.meebox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.meebox.util.SystemUiHider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
		
		Button btnLogin = (Button)findViewById(R.id.btnLogin);//获取按钮资源    
		btnLogin.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	login();
            }    
  
        });  
		
		login_username=(EditText)findViewById(R.id.etUsername);
        login_password=(EditText)findViewById(R.id.etPassword);
	}
	
	private void login(){
        String httpUrl="http://192.168.1.102:8080/web-test/login.jsp";
        HttpPost httpRequest=new HttpPost(httpUrl);
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username",login_username.getText().toString().trim()));
        params.add(new BasicNameValuePair("password",login_password.getText().toString().trim()));
        HttpEntity httpentity = null;
        try {
            httpentity = new UrlEncodedFormEntity(params,"utf8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpRequest.setEntity(httpentity);
        HttpClient httpclient=new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpRequest);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(httpResponse.getStatusLine().getStatusCode()==200)
        {
            String strResult = null;
            try {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Toast.makeText(Loading.this, strResult, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Loading.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Loading.this, "登录失败！", Toast.LENGTH_SHORT).show();
        }
         
    }
}
