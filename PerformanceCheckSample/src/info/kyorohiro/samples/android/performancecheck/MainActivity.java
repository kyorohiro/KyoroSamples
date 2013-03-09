package info.kyorohiro.samples.android.performancecheck;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView mLabel = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLabel = new TextView(this);
		setContentView(mLabel);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add("get");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getTitle().equals("get")) {
			Thread th = new Thread(new PFCheck());
			th.start();
		}
		return super.onMenuItemSelected(featureId, item);
	}


	public void toast(final String message) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast
				.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
				.show();
				mLabel.setText(message);
			}
		});
	}

	public class PFCheck implements Runnable {
		@Override
		public void run() {
			toast("start");
			try {
				PerformanceCheck pfc = new PerformanceCheck();
				pfc.start();
				int[] buffer = new int[512*1024];
				for(int j=0;j<100;j++) {
					for(int i=0;i<buffer.length;i++) {
						buffer[i] = buffer[i]+buffer[i];
					}
				}
				Thread.sleep(3000);
				pfc.stop();
				toast(pfc.getInfo());
			} catch(Exception e) {
				toast("failed");
			}
		}
	}
}
