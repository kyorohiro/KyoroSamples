package info.kyorohiro.samples.android.test;

import info.kyorohiro.samples.android.surfaceview_editor.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

public class CheckForComposingText extends ActivityInstrumentationTestCase2<MainActivity> {

	public CheckForComposingText() {
		super(MainActivity.class);
		//super(MainActivity.class.getPackage().getName(), MainActivity.class);
	}

	public void testHello() {
		getActivity();
		getInstrumentation().waitForIdleSync();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
