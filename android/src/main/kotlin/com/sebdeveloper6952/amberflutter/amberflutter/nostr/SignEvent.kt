package com.sebdeveloper6952.amberflutter.amberflutter.nostr

import android.content.Intent
import android.net.Uri
import com.sebdeveloper6952.amberflutter.amberflutter.models.*

fun CreateSignEventIntent(npub: String?, eventJson: String?, id: String?): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$nostrsignerUri$eventJson"))
    intent.setPackage(amberPackageName)

    intent.putExtra(intentExtraKeyCurrentUser, npub)
    intent.putExtra(intentExtraKeyType, methodSignEvent)
    intent.putExtra(intentExtraKeyId, id)

    return intent
}

fun SignEventResultFromIntent(intent: Intent): Map<String, String> {
    val signedEvent = intent.getStringExtra(intentExtraKeyEvent)
    val sig = intent.getStringExtra(intentExtraKeySignature)
    val id = intent.getStringExtra(intentExtraKeyId)

    val hashMap : HashMap<String, String> = HashMap()

    hashMap[intentExtraKeyEvent] = signedEvent ?: ""
    hashMap[intentExtraKeySignature] = sig ?: ""
    hashMap[intentExtraKeyId] = id ?: ""

    return hashMap
}