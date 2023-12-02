package com.sebdeveloper6952.amberflutter.amberflutter.nostr

import android.content.Intent
import android.net.Uri
import com.sebdeveloper6952.amberflutter.amberflutter.models.*

fun CreateNip44EncryptIntent(plaintext: String?, currentUserNpub: String?, destPubkey: String?): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$plaintext"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodNip44Encrypt)
    intent.putExtra(intentExtraKeyCurrentUser, currentUserNpub)
    intent.putExtra(intentExtraKeyPubKey, destPubkey)

    return intent
}

fun Nip44EncryptResultFromIntent(intent: Intent): Result {
    val cihpertext = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)

    return Result(
        signature = cihpertext,
        id = id,
    )
}

fun CreateNip44DecryptIntent(ciphertext: String?, currentUserNpub: String?, destPubkey: String?): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$ciphertext"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodNip44Decrypt)
    intent.putExtra(intentExtraKeyCurrentUser, currentUserNpub)
    intent.putExtra(intentExtraKeyPubKey, destPubkey)

    return intent
}

fun Nip44DecryptResultFromIntent(intent: Intent): Result {
    val plaintext = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)

    return Result(
        signature = plaintext,
        id = id,
    )
}