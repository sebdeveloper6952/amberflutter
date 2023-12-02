import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'amberflutter_platform_interface.dart';

/// An implementation of [AmberflutterPlatform] that uses method channels.
class MethodChannelAmberflutter extends AmberflutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('amberflutter');

  @override
  Future<String?> getPublicKey() async {
    final pk = await methodChannel.invokeMethod<String>('get_public_key');
    return pk;
  }

  @override
  Future<String?> signEvent(String npub, String event) async {
    final arguments = {"npub": npub, "event": event};
    final signedEvent = await methodChannel.invokeMethod<String>(
      'sign_event',
      arguments,
    );

    return signedEvent;
  }

  @override
  Future<String?> nip04Encrypt(
      String plaintext, String npub, String destPubkey) {
    final arguments = {
      "plaintext": plaintext,
      "npub": npub,
      "dest_pubkey": destPubkey,
    };
    final ciphertext = methodChannel.invokeMethod<String>(
      'nip04_encrypt',
      arguments,
    );

    return ciphertext;
  }

  @override
  Future<String?> nip04Decrypt(
      String ciphertext, String npub, String destPubkey) {
    final arguments = {
      "ciphertext": ciphertext,
      "npub": npub,
      "dest_pubkey": destPubkey,
    };
    final plaintext = methodChannel.invokeMethod<String>(
      'nip04_decrypt',
      arguments,
    );

    return plaintext;
  }

  @override
  Future<String?> nip44Encrypt(
      String plaintext, String npub, String destPubkey) {
    final arguments = {
      "plaintext": plaintext,
      "npub": npub,
      "dest_pubkey": destPubkey,
    };
    final ciphertext = methodChannel.invokeMethod<String>(
      'nip44_encrypt',
      arguments,
    );

    return ciphertext;
  }

  @override
  Future<String?> nip44Decrypt(
      String ciphertext, String npub, String destPubkey) {
    final arguments = {
      "ciphertext": ciphertext,
      "npub": npub,
      "dest_pubkey": destPubkey,
    };
    final plaintext = methodChannel.invokeMethod<String>(
      'nip44_decrypt',
      arguments,
    );

    return plaintext;
  }
}
