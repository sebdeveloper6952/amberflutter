import 'package:amberflutter/amberflutter.dart';

Future<void> getPublicKey() async {
  final amberflutterPlugin = Amberflutter();

  String? pk = await amberflutterPlugin.getPublicKey();
  // if user approves request in amber:
  // pk = npub...
}
