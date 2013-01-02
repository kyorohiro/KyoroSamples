package info.kyorohiro.samples.android.imesample;

import java.lang.ref.WeakReference;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class IMESample extends InputMethodService {
	public static WeakReference<IMESample> mWInstance = new WeakReference<IMESample>(
			null);

	public IMESample() {
		android.util.Log.v("kiyo", "call IMESample");
		mWInstance = new WeakReference<IMESample>(this);
	}

	public static IMESample getInstance() {
		return mWInstance.get();
	}

	//
	// newCursorPosition : when JP, you set 1.
	//
	public void inputComposingText(CharSequence text, int newCursorPosition) {
		IMESample instance = mWInstance.get();
		if (instance == null) {
			return;
		}

		InputConnection input = instance.getCurrentInputConnection();
		if (input == null) {
			return;
		}

		input.setComposingText(text, newCursorPosition);
	}

	//
	// newCursorPosition : when JP, you set 1.
	//
	public void inputCommitText(CharSequence text, int newCursorPosition) {
		IMESample instance = mWInstance.get();
		if (instance == null) {
			return;
		}

		InputConnection input = instance.getCurrentInputConnection();
		if (input == null) {
			return;
		}

		input.commitText(text, newCursorPosition);
	}

	//
	// newCursorPosition : when JP, you set 1.
	//
	public void finishComposingText() {
		IMESample instance = mWInstance.get();
		if (instance == null) {
			return;
		}

		InputConnection input = instance.getCurrentInputConnection();
		if (input == null) {
			return;
		}

		input.finishComposingText();
	}

	public void sendKeycode(KeyEvent event) {
		IMESample instance = mWInstance.get();
		if (instance == null) {
			return;
		}

		InputConnection input = instance.getCurrentInputConnection();
		if (input == null) {
			return;
		}
		input.sendKeyEvent(event);
	}

	//
	//
	//
	@Override
	public void onInitializeInterface() {
		android.util.Log.v("kiyo", "call onInitializeInterface()");
		super.onInitializeInterface();
	}

}
