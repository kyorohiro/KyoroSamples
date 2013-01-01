package info.kyorohiro.samples.android.test;

import info.kyorohiro.samples.android.imesample.IMESample;
import info.kyorohiro.samples.android.surfaceview_editor.Editor;
import info.kyorohiro.samples.android.surfaceview_editor.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

public class CheckForComposingText extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public CheckForComposingText() {
		super(MainActivity.class);
		// super(MainActivity.class.getPackage().getName(), MainActivity.class);
	}

	public void testFirst() {
		MainActivity mainActivity = getActivity();
		getInstrumentation().waitForIdleSync();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    	android.util.Log.v("kiyo","kick showInputConnection");
		Editor editor = mainActivity.getEditor();
		editor.showInputConnection();
		getInstrumentation().waitForIdleSync();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    	android.util.Log.v("kiyo","kick inputCommitText");
		IMESample ime = IMESample.getInstance();
		ime.inputCommitText("おなかがすいた", 1);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
