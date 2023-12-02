package com.sebdeveloper6952.amberflutter.amberflutter.nostr

import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import com.sebdeveloper6952.amberflutter.amberflutter.models.Result
import com.sebdeveloper6952.amberflutter.amberflutter.models.*

fun CreateGetPubkeyIntent(): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(nostrsignerUri))
    intent.setPackage(amberPackageName)
    val permissions = listOf(
        Permission(
            "sign_event",
            22242
        ),
        Permission(
            "nip04_encrypt"
        ),
        Permission(
            "nip04_decrypt"
        ),
        Permission(
            "nip44_encrypt"
        ),
        Permission(
            "nip44_decrypt"
        ),
        Permission(
            "decrypt_zap_event"
        ),
    )

    intent.putExtra("type", methodGetPubkey)
    intent.putExtra("permissions", Gson().toJson(permissions))

    return intent
}

fun GetPubkeyResultFromIntent(intent: Intent): Result {
    val npub = intent.getStringExtra(intentExtraKeySignature)

    return Result(
        signature = npub
    )
}