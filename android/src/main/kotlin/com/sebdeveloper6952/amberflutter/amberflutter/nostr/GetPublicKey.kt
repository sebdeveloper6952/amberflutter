package com.sebdeveloper6952.amberflutter.amberflutter.nostr

import android.content.Intent
import android.net.Uri
import com.sebdeveloper6952.amberflutter.amberflutter.models.*

fun CreateGetPubkeyIntent(permissionsJson: String?): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(nostrsignerUri))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyType, methodGetPubkey)
    intent.putExtra(intentExtraKeyPermissions, permissionsJson)

    return intent
}

fun GetPubkeyResultFromIntent(intent: Intent): Map<String, String> {
    val npub = intent.getStringExtra(intentExtraKeySignature)
    val packageName = intent.getStringExtra(intentExtraKeyPackage)
    val hashMap : HashMap<String, String> = HashMap()

    hashMap[intentExtraKeySignature] = npub!!
    hashMap[intentExtraKeyPackage] = packageName!!

    return hashMap
}