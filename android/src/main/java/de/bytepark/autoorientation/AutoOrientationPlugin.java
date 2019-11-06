package de.bytepark.autoorientation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** AutoOrientationPlugin */
public class AutoOrientationPlugin implements MethodCallHandler {
  private static Registrar sRegistrar;
  
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    sRegistrar = registrar;
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "auto_orientation");
    channel.setMethodCallHandler(new AutoOrientationPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    Activity activity = registrar.activity();
    if (activity == null) {
      Log.e("AutoOrientationPlugin", "activity == null");
      return;
    }
    switch(call.method) {
      case "setLandscapeRight":
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        break;
      case "setLandscapeLeft":
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        break;
      case "setPortraitUp":
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        break;
      case "setPortraitDown":
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        break;
      case "setPortraitAuto":
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        } else {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        break;
      case "setLandscapeAuto":
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        } else {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        break;
      case "setAuto":
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        } else {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
        break;
      default:
        result.notImplemented();
        break;
    }
  }
}
