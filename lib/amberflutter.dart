import 'amberflutter_platform_interface.dart';

class Amberflutter {
  Future<String?> getPublicKey() {
    return AmberflutterPlatform.instance.getPublicKey();
  }

  Future<String?> signEvent(String npub, String event) {
    return AmberflutterPlatform.instance.signEvent(npub, event);
  }
}
