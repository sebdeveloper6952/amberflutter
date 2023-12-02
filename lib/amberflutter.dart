import 'amberflutter_platform_interface.dart';

class Amberflutter {
  Future<String?> getPublicKey() {
    return AmberflutterPlatform.instance.getPublicKey();
  }

  Future<String?> signEvent(String npub, String event) {
    return AmberflutterPlatform.instance.signEvent(npub, event);
  }

  Future<String?> nip04Encrypt(
      String plaintext, String npub, String destPubkey) {
    return AmberflutterPlatform.instance
        .nip04Encrypt(plaintext, npub, destPubkey);
  }

  Future<String?> nip04Decrypt(
      String ciphertext, String npub, String destPubkey) {
    return AmberflutterPlatform.instance
        .nip04Decrypt(ciphertext, npub, destPubkey);
  }

  Future<String?> nip44Encrypt(
      String plaintext, String npub, String destPubkey) {
    return AmberflutterPlatform.instance
        .nip44Encrypt(plaintext, npub, destPubkey);
  }

  Future<String?> nip44Decrypt(
      String ciphertext, String npub, String destPubkey) {
    return AmberflutterPlatform.instance
        .nip44Decrypt(ciphertext, npub, destPubkey);
  }
}
