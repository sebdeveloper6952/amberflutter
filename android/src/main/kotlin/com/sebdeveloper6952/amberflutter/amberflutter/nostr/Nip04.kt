package com.sebdeveloper6952.amberflutter.amberflutter.nostr

import android.content.Intent
import android.net.Uri
import com.sebdeveloper6952.amberflutter.amberflutter.models.*

fun CreateNip04EncryptIntent(plaintext: String?, currentUserNpub: String?, destPubkey: String?): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$plaintext"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodNip04Encrypt)
    intent.putExtra(intentExtraKeyCurrentUser, currentUserNpub)
    intent.putExtra(intentExtraKeyPubKey, destPubkey)

    return intent
}

fun Nip04EncryptResultFromIntent(intent: Intent): Result {
    val cihpertext = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)

    return Result(
        signature = cihpertext,
        id = id,
    )
}

fun CreateNip04DecryptIntent(ciphertext: String?, currentUserNpub: String?, destPubkey: String?): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$ciphertext"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodNip04Decrypt)
    intent.putExtra(intentExtraKeyCurrentUser, currentUserNpub)
    intent.putExtra(intentExtraKeyPubKey, destPubkey)

    return intent
}

fun Nip04DecryptResultFromIntent(intent: Intent): Result {
    val plaintext = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)

    return Result(
        signature = plaintext,
        id = id,
    )
}