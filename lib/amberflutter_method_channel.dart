import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:amberflutter/models.dart';
import 'amberflutter_platform_interface.dart';
import 'dart:convert';

/// An implementation of [AmberflutterPlatform] that uses method channels.
class MethodChannelAmberflutter extends AmberflutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel =
      const MethodChannel('com.sebdeveloper6952.amberflutter');

  @override
  Future<Map<dynamic, dynamic>> getPublicKey(
      {List<Permission>? permissions}) async {
    final arguments = {
      "type": "get_public_key",
      "uri_data": "login",
    };

    if (permissions != null) {
      arguments['permissions'] = jsonEncode(permissions);
    }

    final data = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nostrsigner',
      arguments,
    );

    return data ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> signEvent(
    String npub,
    String event,
    String? id,
  ) async {
    final arguments = {
      "type": "sign_event",
      "current_user": npub,
      "uri_data": event,
      "id": id,
    };

    final data = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nostrsigner',
      arguments,
    );

    return data ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip04Encrypt(
    String plaintext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "type": "nip04_encrypt",
      "uri_data": plaintext,
      "current_user": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final data = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nostrsigner',
      arguments,
    );

    return data ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip04Decrypt(
    String ciphertext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "type": "nip04_decrypt",
      "uri_data": ciphertext,
      "current_user": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final data = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nostrsigner',
      arguments,
    );

    return data ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip44Encrypt(
    String plaintext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "type": "nip44_encrypt",
      "uri_data": plaintext,
      "current_user": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final data = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nostrsigner',
      arguments,
    );

    return data ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip44Decrypt(
    String ciphertext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "type": "nip44_decrypt",
      "uri_data": ciphertext,
      "current_user": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final data = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nostrsigner',
      arguments,
    );

    return data ?? {};
  }
}
