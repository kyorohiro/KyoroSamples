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
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    	android.util.Log.v("kiyo","senario:kick showInputConnection");
		Editor editor = mainActivity.getEditor();
		try {
			for(int j=0;j<50;j++) {
				editor.showInputConnection();
				getInstrumentation().waitForIdleSync();
				IMESample ime = IMESample.getInstance();
				if(ime != null) {
					break;
				}
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//
		// setComposingText
		//
    	android.util.Log.v("kiyo","senario:kick inputComposingText");
		IMESample ime = IMESample.getInstance();
		ime.inputComposingText("おなかがすいた", 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//
		// hideInputConnection
		//
    	android.util.Log.v("kiyo","senario:kick hideInputConnection");
		editor.hideInputConnection();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//
		// setComposingText
		//
    	android.util.Log.v("kiyo","senario:kick inputComposingText");
		ime = IMESample.getInstance();
		ime.inputComposingText("おなかがすいた", 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//
		// commitConnection
		//
    	android.util.Log.v("kiyo","senario:kick inputCommitText");
		ime.inputCommitText("お腹が空いた", 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
