import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'amberflutter_method_channel.dart';

abstract class AmberflutterPlatform extends PlatformInterface {
  /// Constructs a AmberflutterPlatform.
  AmberflutterPlatform() : super(token: _token);

  static final Object _token = Object();

  static AmberflutterPlatform _instance = MethodChannelAmberflutter();

  /// The default instance of [AmberflutterPlatform] to use.
  ///
  /// Defaults to [MethodChannelAmberflutter].
  static AmberflutterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AmberflutterPlatform] when
  /// they register themselves.
  static set instance(AmberflutterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPublicKey() {
    throw UnimplementedError('getPublicKey() has not been implemented.');
  }

  Future<String?> signEvent(String npub, String event) {
    throw UnimplementedError('signEvent() has not been implemented.');
  }

  Future<String?> nip04Encrypt(
      String plaintext, String npub, String destPubkey) {
    throw UnimplementedError('nip04Encrypt() has not been implemented.');
  }

  Future<String?> nip04Decrypt(
      String ciphertext, String npub, String destPubkey) {
    throw UnimplementedError('nip04Decrypt() has not been implemented.');
  }

  Future<String?> nip44Encrypt(
      String plaintext, String npub, String destPubkey) {
    throw UnimplementedError('nip44Encrypt() has not been implemented.');
  }

  Future<String?> nip44Decrypt(
      String ciphertext, String npub, String destPubkey) {
    throw UnimplementedError('nip44Decrypt() has not been implemented.');
  }
}
