import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:amberflutter/models.dart';
import 'amberflutter_platform_interface.dart';
import 'dart:convert';

/// An implementation of [AmberflutterPlatform] that uses method channels.
class MethodChannelAmberflutter extends AmberflutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('amberflutter');

  @override
  Future<Map<dynamic, dynamic>> getPublicKey(
      {List<Permission>? permissions}) async {
    final arguments = {};

    if (permissions != null) {
      arguments['permissions'] = jsonEncode(permissions);
    }

    final pk = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'get_public_key',
      arguments,
    );

    return pk ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> signEvent(
    String npub,
    String event,
    String? id,
  ) async {
    final arguments = {
      "npub": npub,
      "event": event,
      "id": id,
    };

    final signedEvent = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'sign_event',
      arguments,
    );

    return signedEvent ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip04Encrypt(
    String plaintext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "plaintext": plaintext,
      "npub": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final ciphertext = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nip04_encrypt',
      arguments,
    );

    return ciphertext ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip04Decrypt(
    String ciphertext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "ciphertext": ciphertext,
      "npub": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final plaintext = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nip04_decrypt',
      arguments,
    );

    return plaintext ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip44Encrypt(
    String plaintext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "plaintext": plaintext,
      "npub": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final ciphertext = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nip44_encrypt',
      arguments,
    );

    return ciphertext ?? {};
  }

  @override
  Future<Map<dynamic, dynamic>> nip44Decrypt(
    String ciphertext,
    String npub,
    String pubkey,
    String? id,
  ) async {
    final arguments = {
      "ciphertext": ciphertext,
      "npub": npub,
      "pubKey": pubkey,
      "id": id,
    };

    final plaintext = await methodChannel.invokeMethod<Map<dynamic, dynamic>>(
      'nip44_decrypt',
      arguments,
    );

    return plaintext ?? {};
  }
}
