import 'package:flutter/material.dart';
import 'package:amberflutter/amberflutter.dart';
import 'package:nostr/nostr.dart';
import 'dart:convert';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'AmberFlutter Example',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final amber = Amberflutter();
  String _npub = '';
  String _pubkeyHex = '';
  String _text = '';
  String _cipherText = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('AmberFlutter Example'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            FilledButton(
              onPressed: () {
                amber.getPublicKey().then((value) {
                  _npub = value ?? '';
                  _pubkeyHex = Nip19.decodePubkey(_npub);
                  setState(() {
                    _text = value ?? '';
                  });
                });
              },
              child: const Text('Get Public Key'),
            ),
            FilledButton(
              onPressed: () {
                final eventJson = jsonEncode({
                  'kind': 1,
                  'content': 'Hello from Amber Flutter!',
                  'created_at': DateTime.now().millisecondsSinceEpoch / 1000,
                });

                amber.signEvent(_npub, eventJson).then((value) {
                  setState(() {
                    _text = value ?? '';
                  });
                });
              },
              child: const Text('Sign Event'),
            ),
            FilledButton(
              onPressed: () {
                amber
                    .nip04Encrypt(
                  "Hello from Amber Flutter, Nip 04!",
                  _npub,
                  _pubkeyHex,
                )
                    .then((value) {
                  _cipherText = value ?? '';
                  setState(() {
                    _text = value ?? '';
                  });
                });
              },
              child: const Text('Nip 04 Encrypt'),
            ),
            FilledButton(
              onPressed: () {
                amber
                    .nip04Decrypt(
                  _cipherText,
                  _npub,
                  _pubkeyHex,
                )
                    .then((value) {
                  setState(() {
                    _text = value ?? '';
                  });
                });
              },
              child: const Text('Nip 04 Decrypt'),
            ),
            FilledButton(
              onPressed: () {
                amber
                    .nip44Encrypt(
                  "Hello from Amber Flutter, Nip 44!",
                  _npub,
                  _pubkeyHex,
                )
                    .then((value) {
                  _cipherText = value ?? '';
                  setState(() {
                    _text = value ?? '';
                  });
                });
              },
              child: const Text('Nip 44 Encrypt'),
            ),
            FilledButton(
              onPressed: () {
                amber
                    .nip44Decrypt(
                  _cipherText,
                  _npub,
                  _pubkeyHex,
                )
                    .then((value) {
                  setState(() {
                    _text = value ?? '';
                  });
                });
              },
              child: const Text('Nip 44 Decrypt'),
            ),
            Text(_text),
          ],
        ),
      ),
    );
  }
}