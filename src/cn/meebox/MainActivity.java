package cn.meebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private int uid;
	private String sid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent intent = getIntent();
        
		uid = intent.getExtras().getInt("uid");
        sid = intent.getExtras().getString("sid");
        
        TextView text = (TextView)findViewById(R.id.textView1);
        text.setText(uid);
        
	}
}
