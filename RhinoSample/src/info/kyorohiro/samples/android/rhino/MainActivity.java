package info.kyorohiro.samples.android.rhino;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add("sample000");
		menu.add("sample001");
		menu.add("sample002");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if("sample000".equals(item.getTitle())) {
			HelloJS hello = new HelloJS();
			String result = hello.callSampleJS000();
			Toast.makeText(this, ""+result, Toast.LENGTH_LONG).show();
		}
		if("sample001".equals(item.getTitle())) {
			HelloJS hello = new HelloJS();
			String result = hello.callSampleJS001();
			Toast.makeText(this, ""+result, Toast.LENGTH_LONG).show();
		}
		if("sample002".equals(item.getTitle())) {
			HelloJS hello = new HelloJS();
			String result = hello.callSampleJS002();
			Toast.makeText(this, ""+result, Toast.LENGTH_LONG).show();
		}

		return super.onMenuItemSelected(featureId, item);
	}
}
