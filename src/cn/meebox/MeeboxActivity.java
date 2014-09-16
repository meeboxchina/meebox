package cn.meebox;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.view.MotionEvent;

public class MeeboxActivity extends Activity {

	private ImageButton button;
	private PopupWindow popupWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meebox);
		
		button = (ImageButton)findViewById(R.id.ibMenu); 
        //按钮的监听事件
        button.setOnClickListener(popClick);
	}
	
	// 点击弹出左侧菜单的显示方式
	OnClickListener popClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getPopupWindow();
			// 这里是位置显示方式,在屏幕的左侧
			popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);
		}
	};
	
	protected void initPopuptWindow() {
	    // TODO Auto-generated method stub
	    // 获取自定义布局文件activity_popupwindow_left.xml的视图
	    View popupWindow_view = getLayoutInflater().inflate(R.layout.activity_popupwindow_left, null,
	        false);
	    // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
	    popupWindow = new PopupWindow(popupWindow_view, 200, LayoutParams.MATCH_PARENT, true);
	    // 设置动画效果
	    popupWindow.setAnimationStyle(R.style.AnimationFade);
	    // 点击其他地方消失
	    
	    
	    popupWindow_view.setOnTouchListener(new OnTouchListener() {
	      @Override
	      public boolean onTouch(View v, MotionEvent event) {
	        // TODO Auto-generated method stub
	        if (popupWindow != null && popupWindow.isShowing()) {
	          popupWindow.dismiss();
	          popupWindow = null;
	        }
	        return false;
	      }
	    });
	    
	  }
	
	private void getPopupWindow() {
		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
	    } else {
	    	initPopuptWindow();
	    }
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meebox, menu);
		
		menu.addSubMenu("设置");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
}
