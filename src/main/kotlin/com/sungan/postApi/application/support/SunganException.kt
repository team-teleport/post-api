package com.sungan.postApi.application.support

class SunganException : RuntimeException {
    var error: SunganError = SunganError.UNKNOWN_ERROR

    constructor(error: SunganError) : super(error.desc) {
        this.error = error
    }

    constructor(error: SunganError, message: String) : super(error.desc + ": " + message) {
        this.error = error
    }
}