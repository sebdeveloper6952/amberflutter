package com.sebdeveloper6952.amberflutter.amberflutter.models

data class Permission(
    val type: String,
    val kind: Int? = null,
    var checked: Boolean = true
) {
    override fun toString(): String {
        return when (type) {
            "nip04_encrypt" -> {
                "Encrypt data using nip 4"
            }
            "nip04_decrypt" -> {
                "Decrypt data using nip 4"
            }
            "nip44_decrypt" -> {
                "Decrypt data using nip 44"
            }
            "nip44_encrypt" -> {
                "Encrypt data using nip 44"
            }
            "decrypt_zap_event" -> {
                "Decrypt private zaps"
            }
            "sign_event" -> {
                when (kind) {
                    22242 -> "Client authentication"
                    else -> "Event kind $kind"
                }
            }
            else -> type
        }
    }
}