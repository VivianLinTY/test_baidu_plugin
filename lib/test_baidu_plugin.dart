import 'dart:async';

import 'package:flutter/services.dart';
import 'package:test_baidu_plugin/method_utils.dart';

class TestBaiduPlugin {
  static const MethodChannel _channel =
      const MethodChannel('test_baidu_plugin');

  static Future<String> init(bool isDebug) async {
    Map<String, dynamic> arguments = Map();
    arguments.putIfAbsent(MethodUtils.TAG_DEBUG, () => isDebug);
    final String version =
    await _channel.invokeMethod(MethodUtils.INIT.toString(), arguments);
    return version;
  }
}
