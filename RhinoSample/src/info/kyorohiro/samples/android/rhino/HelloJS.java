package info.kyorohiro.samples.android.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class HelloJS {
	public static final String SAMPLE_JS000 = "var i = 1; i+=2;";
	public static final String SAMPLE_JS001 = "function jsfunc(message){return message;}";
	public static final String SAMPLE_JS002 = "out.println('piyo')";

	//
	// evaluate string
	//
	public String callSampleJS000() {
		Context jsContext = Context.enter();

		// //http://d.hatena.ne.jp/sa-y/20100825/1282753670
		jsContext.setOptimizationLevel(-1);
		ScriptableObject scope = jsContext.initStandardObjects();

		//
		Object evaluatedValue = jsContext.evaluateString(scope, SAMPLE_JS000, "<js000>", 1, null);
		String evaluatedString = Context.toString(evaluatedValue);
		return evaluatedString;
	}

	//
	// function
	//
	public String callSampleJS001() {
		Context jsContext = Context.enter();

		jsContext.setOptimizationLevel(-1);
		ScriptableObject scope = jsContext.initStandardObjects();

		jsContext.evaluateString(scope, SAMPLE_JS001, "<js001>", 1, null);
		Object jsValue = scope.get("jsfunc", scope);
		Object args[] = { "world" };
		Function jsFunction = (Function)jsValue;
		Object result = jsFunction.call(jsContext, scope, scope, args);
		String evaluatedString = Context.toString(result);
		return evaluatedString;
	}


	//
	// Java core
	//
	public String callSampleJS002() {
		Context jsContext = Context.enter();
		jsContext.setOptimizationLevel(-1);
		ScriptableObject scope = jsContext.initStandardObjects();
		ScriptableObject.putProperty(scope, "out", Context.javaToJS(new Prin(), scope));
		Object evaluatedValue = jsContext.evaluateString(scope, SAMPLE_JS002, "<js002>", 0, null);
		String evaluatedString = Context.toString(evaluatedValue);
		return "<show Prin#+message> in logcat log"+evaluatedString;
	}

	public static class Prin {
		public void println(String message) {
			android.util.Log.v("kiyo","Prin#"+message);
		}
	}

}
