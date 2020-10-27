package com.wtfff.test_baidu_plugin;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.wtfff.test_baidu_plugin.utils.Log;
import com.wtfff.test_baidu_plugin.utils.MethodUtils;
import com.wtfff.test_baidu_plugin.utils.Utils;

import java.lang.ref.WeakReference;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * TestBaiduPlugin
 */
public class TestBaiduPlugin implements FlutterPlugin, MethodCallHandler {

    private static final String TAG = TestBaiduPlugin.class
            .getSimpleName();

    //mz
    private static final String mzAppId = "";
    private static final String mzAppKey = "";

    //mi
    private static final String xmAppId = "";
    private static final String xmAppKey = "";

    //oppo
    private static final String opAppKey = "";
    private static final String opAppSecret = "";

    private static WeakReference<Context> mContextRef;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "baidu_push_plugin");
        channel.setMethodCallHandler(new TestBaiduPlugin());
        mContextRef = new WeakReference<>(flutterPluginBinding.getApplicationContext());
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "test_baidu_plugin");
        channel.setMethodCallHandler(new TestBaiduPlugin());
        mContextRef = new WeakReference<>(registrar.activity().getApplicationContext());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Log.d(TAG, "method:" + call.method + ", arguments:" + call.arguments.toString());
        if (call.method.equals(MethodUtils.INIT + "")) {
            Boolean isDebug = call.argument(MethodUtils.TAG_DEBUG);
            Log.wtp(TAG, "Init debug: " + isDebug);
            Log.sIsDebug = null != isDebug && isDebug;
            registerBaiduPush(result);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        Log.v(TAG, "onDetachedFromEngine");
        PushManager.stopWork(binding.getApplicationContext());
    }

    // setup api_key
    private static void initWithApiKey(Context context, Result result) {
        Log.v(TAG, "initWithApiKey");

        PushManager.enableHuaweiProxy(context, true);

        PushManager.enableMeizuProxy(context, true, mzAppId, mzAppKey);

        PushManager.enableOppoProxy(context, true, opAppKey, opAppSecret);

        PushManager.enableXiaomiProxy(context, true, xmAppId, xmAppKey);

        PushManager.enableVivoProxy(context, true);

        MyPushMessageReceiver.setResultCallback(result);
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(context, "api_key"));
    }

    private static void registerBaiduPush(Result result) {
        Log.v(TAG, "registerBaiduPush.");
        if (null == mContextRef || null == mContextRef.get()) {
            result.success(null);
            return;
        }
        Context context = mContextRef.get();
        Utils.logStringCache = Utils.getLogText(context);
        int writePermission = ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission == PackageManager.PERMISSION_GRANTED) {
            initWithApiKey(context, result);
        } else {
            result.success(null);
            Log.w(TAG, "Has no permission WRITE_EXTERNAL_STORAGE.");
        }
    }
}
