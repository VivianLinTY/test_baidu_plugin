package com.wtfff.test_baidu_plugin;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

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

    /**
     * 魅族代理需要的魅族appid和appkey，请到魅族推送官网申请
     **/
    private static final String mzAppId = "";
    private static final String mzAppKey = "";

    /**
     * 小米代理需要的小米appid和appkey，请到小米推送官网申请
     **/
    private static final String xmAppId = "";
    private static final String xmAppKey = "";

    /**
     * OPPO代理需要的OPPO appkey和appsecret，请到OPPO推送官网申请
     **/
    private static final String opAppKey = "";
    private static final String opAppSecret = "";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "test_baidu_plugin");
        channel.setMethodCallHandler(new TestBaiduPlugin());
        registerBaiduPush(flutterPluginBinding.getApplicationContext());
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
        registerBaiduPush(registrar.context());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

    // api_key 绑定
    private static void initWithApiKey(Context context) {
        // 开启华为代理，如需开启，请参考华为代理接入文档
        //！！应用需要已经在华为推送官网注册
        PushManager.enableHuaweiProxy(context, true);
        // 开启魅族代理，如需开启，请参考魅族代理接入文档
        //！！需要将mzAppId和mzAppKey修改为自己应用在魅族推送官网申请的APPID和APPKEY
        PushManager.enableMeizuProxy(context, true, mzAppId, mzAppKey);
        // 开启OPPO代理，如需开启，请参考OPPO代理接入文档
        //！！需要将opAppKey和opAppSecret修改为自己应用在OPPO推送官网申请的APPKEY和APPSECRET
        PushManager.enableOppoProxy(context, true, opAppKey, opAppSecret);
        // 开启小米代理，如需开启，请参考小米代理接入文档
        //！！需要将xmAppId和xmAppKey修改为自己应用在小米推送官网申请的APPID和APPKEY
        PushManager.enableXiaomiProxy(context, true, xmAppId, xmAppKey);
        // 开启VIVO代理，如需开启，请参考VIVO代理接入文档
        //！！需要将AndroidManifest.xml中com.vivo.push.api_key和com.vivo.push.app_id修改为自己应用在VIVO推送官网申请的APPKEY和APPID
        PushManager.enableVivoProxy(context, true);
        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        // ！！请将AndroidManifest.xml api_key 字段值修改为自己的 api_key 方可使用 ！！
        //！！ATTENTION：You need to modify the value of api_key to your own in AndroidManifest.xml to use this Demo !!
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(context, "api_key"));
    }

    private static void registerBaiduPush(Context context) {
        Utils.logStringCache = Utils.getLogText(context);
        int writePermission = ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission == PackageManager.PERMISSION_GRANTED) {
            initWithApiKey(context);
        }
    }
}
