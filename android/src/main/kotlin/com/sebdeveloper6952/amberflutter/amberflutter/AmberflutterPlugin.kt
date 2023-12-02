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
        val intent = CreateGetPubkeyIntent()
        _activity?.startActivityForResult(intent, _intentRequestCodeGetPublicKey)
      }
      methodSignEvent -> {
        val npub = call.argument<String>("npub")
        val eventJson = call.argument<String>("event")
        val intent = CreateSignEventIntent(npub, eventJson)
        _activity?.startActivityForResult(intent, _intentRequestCodeSignEvent)
      }
      methodNip04Encrypt -> {
        val npub = call.argument<String>("npub")
        val plaintext = call.argument<String>("plaintext")
        val destPubkey = call.argument<String>("dest_pubkey")
        val intent = CreateNip04EncryptIntent(plaintext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip04Encrypt)
      }
      methodNip04Decrypt -> {
        val npub = call.argument<String>("npub")
        val ciphertext = call.argument<String>("ciphertext")
        val destPubkey = call.argument<String>("dest_pubkey")
        val intent = CreateNip04DecryptIntent(ciphertext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip04Decrypt)
      }
      methodNip44Encrypt -> {
        val npub = call.argument<String>("npub")
        val plaintext = call.argument<String>("plaintext")
        val destPubkey = call.argument<String>("dest_pubkey")
        val intent = CreateNip44EncryptIntent(plaintext, npub, destPubkey)
        _activity?.startActivityForResult(intent, _intentRequestCodeNip44Encrypt)
      }
      methodNip44Decrypt -> {
        val npub = call.argument<String>("npub")
        val ciphertext = call.argument<String>("ciphertext")
        val destPubkey = call.argument<String>("dest_pubkey")
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

    when (requestCode) {
      _intentRequestCodeGetPublicKey -> intent?.let {
        val amberResult = GetPubkeyResultFromIntent(it)
        _result.success(amberResult.signature)
      }
      _intentRequestCodeSignEvent -> intent?.let {
        val amberResult = SignEventResultFromIntent(it)
        _result.success(amberResult.event)
      }
      _intentRequestCodeNip04Encrypt -> intent?.let {
        val amberResult = Nip04EncryptResultFromIntent(it)
        _result.success(amberResult.signature)
      }
      _intentRequestCodeNip04Decrypt -> intent?.let {
        val amberResult = Nip04DecryptResultFromIntent(it)
        _result.success(amberResult.signature)
      }
      _intentRequestCodeNip44Encrypt -> intent?.let {
        val amberResult = Nip44EncryptResultFromIntent(it)
        _result.success(amberResult.signature)
      }
      _intentRequestCodeNip44Decrypt -> intent?.let {
        val amberResult = Nip44DecryptResultFromIntent(it)
        _result.success(amberResult.signature)
      }
      else -> {
        _result.error("error","","")
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
}
