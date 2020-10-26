import 'dart:async';

import 'package:flutter/services.dart';

class TestBaiduPlugin {
  static const MethodChannel _channel =
      const MethodChannel('test_baidu_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
