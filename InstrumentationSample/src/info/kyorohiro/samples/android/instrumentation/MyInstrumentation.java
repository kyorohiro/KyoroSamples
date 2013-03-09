package info.kyorohiro.samples.android.instrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.KeyEvent;

/**
 * 
 * Instrumentation から Activityを起動するサンプル
 *
 * ※ AndroidManifest.xmlに以下を追加してください。
 *     <instrumentation 
 *     android:targetPackage="info.kyorohiro.samples.android.instrumentation"
 * 	   android:name="info.kyorohiro.samples.android.instrumentation.MyInstrumentation"/>
 *   adb shell am instrument -w .kyorohiro.samples.android.instrumentation/info.kyorohiro.samples.android.instrumentation.MyInstrumentation
 */
public class MyInstrumentation extends Instrumentation 
{
	public static MyInstrumentation instance = null;
	public static final String TAG = MyInstrumentation.class.getSimpleName();
	public static void log(String log) {
		android.util.Log.v(TAG, log);
	}
	public MyInstrumentation(){
		super();
		log("new");
	}
	
	public static Instrumentation getInstrumentation() {
		return instance;
	}

	@Override
	public void onCreate(Bundle arguments) {
		Debug.waitForDebugger();
		log("onCreate()");
		super.onCreate(arguments);
		start();
	}

	@Override
	public void onStart() {
		instance = this;
		log("onStart()");
		super.onStart();
		Thread testSuites = new Thread(){
			public void run(){
				runTestSenario();
			}
		};
		testSuites.start();
	}

	@Override
	public void onDestroy() {
		log("onDestroy()");
		instance = null;
		super.onDestroy();
	}

	@Override
	public void callActivityOnCreate(Activity activity, Bundle icicle) {
		log("callActivityOnCreate()");
		super.callActivityOnCreate(activity, icicle);
	}

	@Override
	public void callActivityOnResume(Activity activity) {
		log("callActivityOnResume()");
		super.callActivityOnResume(activity);
	}

	@Override
	public void callActivityOnDestroy(Activity activity) {
		log("callActivityOnDestroy()");
		super.callActivityOnDestroy(activity);
	}


	
	private void runTestSenario() {
		log("#runTestSenario()");
		Instrumentation inst = MyInstrumentation.getInstrumentation();

		log("#startActivity()");
		startActivity(inst);

		log("#startPerformanceCheck()");
		startPerformance(inst);
		inst.waitForIdleSync();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log("#stopPerformanceCheck()");
		stopPerformance(inst);

		log("#finish()");
		finishInstrumentation(inst);
	}

	public static void startActivity(Instrumentation inst) {
		Context c = inst.getContext();
		String packageName = MainActivity.class.getPackage().getName();
		String className = MainActivity.class.getName();
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClassName(packageName, className);
		c.startActivity(intent);
	}

	public static void startPerformance(Instrumentation inst) {
		inst.setAutomaticPerformanceSnapshots();
		inst.startPerformanceSnapshot();		
	}

	public static void stopPerformance(Instrumentation inst) {
		inst.endPerformanceSnapshot();
	}

	public static void finishInstrumentation(Instrumentation inst) {
		Bundle b = new Bundle();
		inst.finish(Activity.RESULT_OK, b);		
	}
}
