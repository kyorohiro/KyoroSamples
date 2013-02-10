package info.kyorohiro.samples.android.emma.test;

import info.kyorohiro.samples.android.emma.MainActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class TestForMainActivity extends ActivityInstrumentationTestCase2<MainActivity>{

	public TestForMainActivity() {
		super(MainActivity.class);
	}

	public void testHello() throws InterruptedException {
		Activity activity = getActivity();
		Thread.sleep(10000);
	}

}
