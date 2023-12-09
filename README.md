# amberflutter

A Flutter wrapper for [Amber](https://github.com/greenart7c3/Amber/tree/master) (Nostr Signer).

## Rationale

Provide a bridge between your app and Amber, to enable app developers to easily use the functionality provided by Amber.

## Installation

With flutter:

```
flutter pub add amberflutter
```

Or add to your `pubspec.yaml`:

```
dependencies:
  amberflutter: ^0.0.1
```

## Usage

Documentation is taken from [Amber Docs](https://github.com/greenart7c3/Amber/blob/master/README.md). Please refer to them if you have any question about how to use Amber.

_Import in your code_

```
import 'package:amberflutter/amberflutter.dart';
```

_Get Public Key_

Get the current user public key (npub). This request also allows to send permissions so the user can approve them forever. See the [Amber Docs](https://github.com/greenart7c3/Amber/blob/master/README.md) for the list of available permissions.

```
final amber = Amberflutter();

amber.getPublicKey(
  permissions: [
    Permission(
      type: "sign_event",
    ),
  ],
).then((value) {
  print("npub: ${value['signature']}");
});
```

_Sign Event_

```
final amber = Amberflutter();
final eventJson = jsonEncode({
  'id': '',
  'pubkey': Nip19.decodePubkey(_npub),
  'kind': 1,
  'content': 'Hello from Amber Flutter!',
  'created_at':
      (DateTime.now().millisecondsSinceEpoch / 1000).round(),
  'tags': [],
  'sig': '',
});

amber.signEvent(
  currentUser: "<your_npub_here>",
  eventJson: eventJson,
).then((value) {
  print("signed event: ${value['event']}");
});
```

_Nip 04 Encrypt_

```
final amber = Amberflutter();

amber.nip04Encrypt(
  plaintext: "Hello from Amber Flutter, Nip 04!",
  currentUser: "<your_npub_here>",
  pubKey: "<hex_pubkey_to_encrypt>",
).then((value) {
  print("ciphertext: ${value['signature']}")
});
```

_Nip 44 Encrypt_

```
amber.nip44Encrypt(
  plaintext: "Hello from Amber Flutter, Nip 44!",
  currentUser: "<your_npub_here>",
  pubKey: "<hex_pubkey_to_encrypt>",
).then((value) {
  print("ciphertext: ${value['signature']}")
});
```

_Nip 04 Decrypt_

```
final amber = Amberflutter();

amber.nip04Decrypt(
  ciphertext: "<message_encrypted_with_nip04_here>",
  currentUser: "<your_npub_here>",
  pubKey: "<hex_pubkey_to_decrypt>",
).then((value) {
  print("plaintext: ${value['signature']}")
});
```

_Nip 44 Decrypt_

```
final amber = Amberflutter();

amber.nip44Decrypt(
  ciphertext: "<message_encrypted_with_nip44_here>",
  currentUser: "<your_npub_here>",
  pubKey: "<hex_pubkey_to_decrypt>",
).then((value) {
  print("plaintext: ${value['signature']}")
});
```
