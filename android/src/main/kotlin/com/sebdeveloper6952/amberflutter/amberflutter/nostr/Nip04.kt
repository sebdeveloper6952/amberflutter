package com.sebdeveloper6952.amberflutter.amberflutter.nostr

import android.content.Intent
import android.net.Uri
import com.sebdeveloper6952.amberflutter.amberflutter.models.*

fun CreateNip04EncryptIntent(
    plaintext: String?,
    currentUserNpub: String?,
    destPubkey: String?,
    id: String?,
): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$plaintext"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodNip04Encrypt)
    intent.putExtra(intentExtraKeyCurrentUser, currentUserNpub)
    intent.putExtra(intentExtraKeyPubKey, destPubkey)
    intent.putExtra(intentExtraKeyId, id)

    return intent
}

fun Nip04EncryptResultFromIntent(intent: Intent): Map<String, String> {
    val ciphertext = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)
    val hashMap : HashMap<String, String> = HashMap()

    hashMap[intentExtraKeySignature] = ciphertext ?: ""
    hashMap[intentExtraKeyId] = id ?: ""

    return hashMap
}

fun CreateNip04DecryptIntent(
    ciphertext: String?,
    currentUserNpub: String?,
    destPubkey: String?,
    id: String?,
): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$ciphertext"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodNip04Decrypt)
    intent.putExtra(intentExtraKeyCurrentUser, currentUserNpub)
    intent.putExtra(intentExtraKeyPubKey, destPubkey)
    intent.putExtra(intentExtraKeyId, id)

    return intent
}

fun Nip04DecryptResultFromIntent(intent: Intent): Map<String, String> {
    val plaintext = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)
    val hashMap : HashMap<String, String> = HashMap()

    hashMap[intentExtraKeySignature] = plaintext ?: ""
    hashMap[intentExtraKeyId] = id ?: ""

    return hashMap
}