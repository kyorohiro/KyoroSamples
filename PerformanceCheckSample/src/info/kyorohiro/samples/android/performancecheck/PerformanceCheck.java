package info.kyorohiro.samples.android.performancecheck;

import java.util.LinkedHashMap;
import java.util.Set;

import android.os.SystemClock;

public class PerformanceCheck {

	//
	// cpu
	private long mSystemClockUptimeMillis = 0;
	private long mElapsedCpuTime = 0;
	private long mSystemCurrentTimeMillis = 0;
	private long mThreadCpuTimeNanos = 0;

	
	//
	// memory
	private long mNativeMax = 0;
	private long mNativeAllocated = 0;
	private long mNativeFree = 0;
	private long mDalvikMax = 0;
	private long mDalvikFree = 0;
	private int mGlobalAllocCount = 0;
	private int mGlobalAllocSize = 0;
	private int mGlobalFreedCount = 0;
	private int mGlobalFreedSize = 0;
	private int mGlobalGcInvocationCount = 0;

	//
	private int mPreBinderSentTransactions = 0;
	private int mPreBinderReceivedTransactions = 0;
	private int mBinderSentTransactions = 0;
	private int mBinderReceivedTransactions = 0;

	private android.os.Debug.MemoryInfo mMemInfo = null; 
	private android.os.Debug.MemoryInfo mPrevMemInfo = null; 

	//
	// total
	private LinkedHashMap<String, String> mInfo = new LinkedHashMap<String, String>();// HashLMap<String,
																						// String>();

	//
	//

	public void start() {
		mInfo.clear();
		mPreBinderSentTransactions = android.os.Debug.getBinderSentTransactions();
		mPreBinderReceivedTransactions = android.os.Debug.getBinderReceivedTransactions();
		Runtime.getRuntime().gc();
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();
		android.os.Debug.MemoryInfo memInfo = new android.os.Debug.MemoryInfo();
		android.os.Debug.getMemoryInfo(memInfo);
		mPrevMemInfo = memInfo;
		startAllocCounting();
		
		mSystemCurrentTimeMillis = System.currentTimeMillis();
		mThreadCpuTimeNanos = android.os.Debug.threadCpuTimeNanos();	
		mSystemClockUptimeMillis = SystemClock.uptimeMillis();
		mElapsedCpuTime = android.os.Process.getElapsedCpuTime();
	}

	public void stop() {
		mSystemClockUptimeMillis = SystemClock.uptimeMillis() - mSystemClockUptimeMillis;
		mElapsedCpuTime          = android.os.Process.getElapsedCpuTime() - mElapsedCpuTime;
		mSystemCurrentTimeMillis = System.currentTimeMillis() - mSystemCurrentTimeMillis;
		mThreadCpuTimeNanos      = android.os.Debug.threadCpuTimeNanos() - mThreadCpuTimeNanos;	

		stopAllocCounting();

		//
		mNativeMax               = android.os.Debug.getNativeHeapSize();
		mNativeAllocated         = android.os.Debug.getNativeHeapAllocatedSize();
		mNativeFree              = android.os.Debug.getNativeHeapFreeSize();


		//
		android.os.Debug.MemoryInfo memInfo = new android.os.Debug.MemoryInfo();
		android.os.Debug.getMemoryInfo(memInfo);
		mMemInfo = memInfo;
		Runtime runtime = Runtime.getRuntime();
		mDalvikMax = runtime.totalMemory();
		mDalvikFree = runtime.freeMemory();

		//
		mBinderSentTransactions = android.os.Debug.getBinderSentTransactions();
		mBinderReceivedTransactions = android.os.Debug.getBinderReceivedTransactions();
		mGlobalAllocCount = android.os.Debug.getGlobalAllocCount();
		mGlobalAllocSize = android.os.Debug.getGlobalAllocSize();
		mGlobalFreedCount = android.os.Debug.getGlobalFreedCount();
		mGlobalFreedSize = android.os.Debug.getGlobalFreedSize();
		mGlobalGcInvocationCount = android.os.Debug.getGlobalGcInvocationCount();

		infoToMap();
	}

	public void infoToMap() {
		//
		mInfo.put("SystemClock.uptimeMillis()",            "" +mSystemCurrentTimeMillis+"ms:");
		mInfo.put("Process.getElapsedCpuTime()",           "" + mElapsedCpuTime+"ms:");
		mInfo.put("System.currentTimeMillis()",            "" + mSystemCurrentTimeMillis+"ms:");
		mInfo.put("Runtime.totalMemory()",                 "" +(mDalvikMax/(1024))+"kb:" + mDalvikMax);
		mInfo.put("Runtime.freeMemory()",                  "" +(mDalvikFree/(1024))+"kb:" +mDalvikFree);
		mInfo.put("Debug.threadCpuTimeNanos()",            "" + (mThreadCpuTimeNanos/(1000*1000))+"ms:"+mThreadCpuTimeNanos);
		mInfo.put("Debug.getNativeHeapSize()",             "" +(mNativeMax/1024)+"kb:"+ mNativeMax);
		mInfo.put("Debug.getNativeHeapAllocatedSize()",    "" +(mNativeAllocated/1024)+"kb:"+ mNativeAllocated);
		mInfo.put("Debug.getNativeHeapFreeSize()",         "" +(mNativeFree/1024)+"kb:"+mNativeFree);	
		mInfo.put("Debug.getBinderSentTransactions()",     "" + mPreBinderSentTransactions);
		mInfo.put("pre->Debug.getBinderReceivedTransactions()", "" + mPreBinderReceivedTransactions);
		mInfo.put("pre->Debug.getBinderSentTransactions()",     "" + mBinderSentTransactions);
		mInfo.put("Debug.getBinderReceivedTransactions()", "" + mBinderReceivedTransactions);
		mInfo.put("Debug.getGlobalAllocCount()",           "" + mGlobalAllocCount);
		mInfo.put("Debug.getGlobalFreedCount()",           "" + mGlobalFreedCount);
		mInfo.put("Debug.getGlobalGcInvocationCount()",    "" + mGlobalGcInvocationCount);
		mInfo.put("Debug.getGlobalAllocSize()",            "" +(mGlobalAllocSize/1024)+"kb:"+ mGlobalAllocSize);
		mInfo.put("Debug.getGlobalFreedSize()",            "" +(mGlobalFreedSize/1024)+"kb:"+mGlobalFreedSize);
		//
		//
		mInfo.put("MemInfo.dalvikPss",                     "" + mMemInfo.dalvikPss+"kb:diff"+(mMemInfo.dalvikPss-mPrevMemInfo.dalvikPss));
		mInfo.put("MemInfo.dalvikPrivateDirty",            "" + mMemInfo.dalvikPrivateDirty+"kb:diff"+(mMemInfo.dalvikPrivateDirty-mPrevMemInfo.dalvikPrivateDirty));
		mInfo.put("MemInfo.dalvikSharedDirty",             "" + mMemInfo.dalvikSharedDirty+"kb:diff"+(mMemInfo.dalvikSharedDirty-mPrevMemInfo.dalvikSharedDirty));
		mInfo.put("MemInfo.otherPss",                      "" + mMemInfo.otherPss+"kb:diff"+(mMemInfo.otherPss-mPrevMemInfo.otherPss));
		mInfo.put("MemInfo.otherPrivateDirty",             "" + mMemInfo.otherPrivateDirty+"kb:diff"+(mMemInfo.otherPrivateDirty-mPrevMemInfo.otherPrivateDirty));
		mInfo.put("MemInfo.otherSharedDirty",              "" + mMemInfo.otherSharedDirty+"kb:diff"+(mMemInfo.otherSharedDirty-mPrevMemInfo.otherSharedDirty));
	}

	public String getInfo() {
		StringBuilder builder = new StringBuilder();
		Set<String> keys = mInfo.keySet();
		for(String k : keys) {
			builder.append("" + k + " : " + mInfo.get(k) + "\r\n");
		}
		return builder.toString();
	}

	public static void startAllocCounting() {
		Runtime.getRuntime().gc();
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();
		android.os.Debug.resetAllCounts();
		android.os.Debug.startAllocCounting();
	}

	public static void stopAllocCounting() {
		Runtime.getRuntime().gc();
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();
		android.os.Debug.stopAllocCounting();
	}
}
