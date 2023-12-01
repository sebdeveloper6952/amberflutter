package com.sebdeveloper6952.amberflutter.amberflutter

import com.sebdeveloper6952.amberflutter.amberflutter.models.Permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.annotation.NonNull
import com.google.gson.Gson

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.PluginRegistry


/** AmberflutterPlugin */
class AmberflutterPlugin: FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var _channel : MethodChannel
  private var _activity: Activity? = null
  private lateinit var _result: MethodChannel.Result

  private val _signerPkgName = "com.greenart7c3.nostrsigner"
  private val _nostrSignerUri = "nostrsigner:"
  private val _intentRequestCodeGetPublicKey = 0
  private val _intentRequestCodeSignEvent = 1
  private val _methodGetPubkey = "get_public_key"
  private val _methodSignEvent = "sign_event"
  private val _resultFieldSignature = "signature"



  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    _channel = MethodChannel(flutterPluginBinding.binaryMessenger, "amberflutter")
    _channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    _result = result

    when (call.method) {
      _methodGetPubkey -> _onMethodCallGetPublicKey()
      _methodSignEvent -> {
        val npub = call.argument<String>("npub")
        val eventJson = call.argument<String>("event")

        _onMethodCallSignEvent(npub, eventJson)
      }
      else -> {
        result.notImplemented()
        return
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?): Boolean {
    if (resultCode != Activity.RESULT_OK) {
      _result.error("error","user rejected request","")
      return false
    }

    when (requestCode) {
      _intentRequestCodeGetPublicKey -> intent?.let { _onGetPublicKeyResult(it) }
      _intentRequestCodeSignEvent -> intent?.let { _onSignEventResult(it) }
      else -> {
        _result.error("","","")
        return false
      }
    }

    return true
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    _channel.setMethodCallHandler(null)
  }

  override fun onDetachedFromActivity() {
    _activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    _activity = binding.activity
    binding.addActivityResultListener(this)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    _activity = binding.activity
    binding.addActivityResultListener(this)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    _activity = null
  }

  fun _onMethodCallGetPublicKey() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(_nostrSignerUri))
    intent.setPackage(_signerPkgName)
    val permissions = listOf(
      Permission(
        "sign_event",
        22242
      ),
      Permission(
        "nip04_encrypt"
      ),
      Permission(
        "nip04_decrypt"
      ),
      Permission(
        "nip44_encrypt"
      ),
      Permission(
        "nip44_decrypt"
      ),
      Permission(
        "decrypt_zap_event"
      ),
    )

    intent.putExtra("type", _methodGetPubkey)
    intent.putExtra("permissions", Gson().toJson(permissions))

    _activity?.startActivityForResult(intent, _intentRequestCodeGetPublicKey)
  }

  fun _onMethodCallSignEvent(currentUserNpub: String?, eventJson: String?) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$_nostrSignerUri$eventJson"))
    intent.setPackage(_signerPkgName)

    intent.putExtra("type", _methodSignEvent)
    intent.putExtra("current_user", currentUserNpub)

    _activity?.startActivityForResult(intent, _intentRequestCodeSignEvent)
  }

  fun _onGetPublicKeyResult(intent: Intent) {
    val npub = intent.getStringExtra(_resultFieldSignature)
    _result.success(npub)
  }

  fun _onSignEventResult(intent: Intent) {
    val signature = intent.getStringExtra("signature")
    val id = intent.getStringExtra("id")
    val signedEventJson = intent.getStringExtra("event")

    _result.success(signedEventJson)
  }
}
