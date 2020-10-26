import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:test_baidu_plugin/test_baidu_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('test_baidu_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await TestBaiduPlugin.platformVersion, '42');
  });
}
