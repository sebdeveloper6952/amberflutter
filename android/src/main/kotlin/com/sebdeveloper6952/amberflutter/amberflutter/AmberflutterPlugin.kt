package com.sebdeveloper6952.amberflutter.amberflutter

import com.sebdeveloper6952.amberflutter.amberflutter.models.*
import com.sebdeveloper6952.amberflutter.amberflutter.nostr.*

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

  private val _intentRequestCodeGetPublicKey = 0
  private val _intentRequestCodeSignEvent = 1
  private val _intentRequestCodeNip04Encrypt = 2
  private val _intentRequestCodeNip04Decrypt = 3
  private val _intentRequestCodeNip44Encrypt = 4
  private val _intentRequestCodeNip44Decrypt = 5

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    _channel = MethodChannel(flutterPluginBinding.binaryMessenger, "amberflutter")
    _channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    _result = result

    when (call.method) {
      methodGetPubkey -> {
        val permissions = call.argument<String>(intentExtraKeyPermissions)
        val intent = CreateGetPubkeyIntent(permissions)
        _activity?.startActivityForResult(intent, _intentRequestCodeGetPublicKey)
      }
      methodSignEvent -> {
        val npub = call.argument<String>(intentExtraKeyNpub)
        val eventJson = call.argument<String>(intentExtraKeyEvent)
        val intent = CreateSignEventIntent(npub, eventJson)
        _activity?.startActivityForResult(intent, _intentRequestCodeSignEvent)
      }
      methodNip04Encrypt -> {
        val npub = call.argument<String>(intentExtraKeyNpub)
        val plaintext = call.argument<String>(intentExtraKeyPlaintext)
        val destPubkey = call.argument<String>(intentExtraKeyPubKey)
        val intent = CreateNip04EncryptIntent(plaintext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip04Encrypt)
      }
      methodNip04Decrypt -> {
        val npub = call.argument<String>(intentExtraKeyNpub)
        val ciphertext = call.argument<String>(intentExtraKeyCiphertext)
        val destPubkey = call.argument<String>(intentExtraKeyPubKey)
        val intent = CreateNip04DecryptIntent(ciphertext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip04Decrypt)
      }
      methodNip44Encrypt -> {
        val npub = call.argument<String>(intentExtraKeyNpub)
        val plaintext = call.argument<String>(intentExtraKeyPlaintext)
        val destPubkey = call.argument<String>(intentExtraKeyPubKey)
        val intent = CreateNip44EncryptIntent(plaintext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip44Encrypt)
      }
      methodNip44Decrypt -> {
        val npub = call.argument<String>(intentExtraKeyNpub)
        val ciphertext = call.argument<String>(intentExtraKeyCiphertext)
        val destPubkey = call.argument<String>(intentExtraKeyPubKey)
        val intent = CreateNip44DecryptIntent(ciphertext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip44Decrypt)
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

    lateinit var amberResult: Map<String, String>

    when (requestCode) {
      _intentRequestCodeGetPublicKey -> intent?.let {
        amberResult = GetPubkeyResultFromIntent(it)
      }
      _intentRequestCodeSignEvent -> intent?.let {
        amberResult = SignEventResultFromIntent(it)
      }
      _intentRequestCodeNip04Encrypt -> intent?.let {
        amberResult = Nip04EncryptResultFromIntent(it)
      }
      _intentRequestCodeNip04Decrypt -> intent?.let {
        amberResult = Nip04DecryptResultFromIntent(it)
      }
      _intentRequestCodeNip44Encrypt -> intent?.let {
        amberResult = Nip44EncryptResultFromIntent(it)
      }
      _intentRequestCodeNip44Decrypt -> intent?.let {
        amberResult = Nip44DecryptResultFromIntent(it)
      }
      else -> {
        _result.error("error","unknown request code","")
        return false
      }
    }

    _result.success(amberResult)

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
}
