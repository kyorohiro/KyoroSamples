package info.kyorohiro.samples.android.imesample;

import java.lang.ref.WeakReference;

import android.inputmethodservice.InputMethodService;
import android.view.inputmethod.InputConnection;

public class IMESample extends InputMethodService {
	public static WeakReference<IMESample> mWInstance = null;

	public IMESample() {
		mWInstance = new WeakReference<IMESample>(this);
	}

	// 
	// newCursorPosition : when JP, you set 1. 
	//
	public void inputComposingText(CharSequence text, int newCursorPosition) {
		IMESample instance = mWInstance.get();
		if(instance == null) {
			return;
		}

		InputConnection input = instance.getCurrentInputConnection();
		if(input == null) {
			return;
		}

		input.setComposingText(text, newCursorPosition);
	}


	// 
	// newCursorPosition : when JP, you set 1. 
	//
	public void inputCommitText(CharSequence text, int newCursorPosition) {
		IMESample instance = mWInstance.get();
		if(instance == null) {
			return;
		}

		InputConnection input = instance.getCurrentInputConnection();
		if(input == null) {
			return;
		}

		input.commitText(text, newCursorPosition);
	}



}