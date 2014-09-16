package cn.meebox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);  
		
		//builder.setIcon(R.drawable.icon);  
	    builder.setTitle("你确定要离开吗？");  
	    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		//这里添加点击确定后的逻辑
	    		dialog.dismiss();
	    		TestActivity.this.finish();
	    	}  
	    });
	    
	    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		//这里添加点击确定后的逻辑
	    		dialog.dismiss();
	    		
	    	}
	    });  
	    
	    builder.setNeutralButton("不确定", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		//这里添加点击确定后的逻辑
	    		//dialog.dismiss();
	    	}
	    });
	    
	    
	    builder.setView(new EditText(this));
	    
	    builder.create().show();  
	}
}
