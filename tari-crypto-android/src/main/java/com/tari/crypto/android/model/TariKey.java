/*
 * Copyright 2020 The Tari Project
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of
 * its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.tari.crypto.android.model;

import android.util.Pair;

import com.tari.crypto.android.exception.TariCryptoException;

/**
 * A cryptographic key that consists of bytes.
 * Contains method for signing messages and verifying
 * message signatures.
 *
 * @author The Tari Development Team
 */
public class TariKey {

    public final byte[] bytes;

    /**
     * Native method that returns the description for the
     * given error code.
     */
    static native String jniLookupErrorCode(int errorCode);

    /**
     * Native method that signs a message.
     *
     * @return (signature, nonce)
     */
    static native Pair<byte[], byte[]> jniSignMessage(byte[] privateKey,
                                                      String message);

    /**
     * Native method that verifies a message signature.
     *
     * @return true if the verification was successful
     */
    static native boolean jniVerifyMessage(byte[] publicKey,
                                           String message,
                                           byte[] nonce,
                                           byte[] signature,
                                           int errorCode);

    public TariKey(final byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Sign a given message - used with a private key.
     */
    public TariSignedMessage signMessage(final String message) {
        final Pair<byte[], byte[]> signedMessage = jniSignMessage(bytes, message);
        return new TariSignedMessage(
                message,
                signedMessage.first,
                signedMessage.second
        );
    }

    /**
     * Verify a given message signature.
     *
     * @throws TariCryptoException translated error from the native layer
     */
    public boolean verifySignature(final TariSignedMessage signedMessage)
            throws TariCryptoException {
        int errorCode = 0;
        final boolean verificationIsSuccessful = jniVerifyMessage(
                bytes,
                signedMessage.message,
                signedMessage.nonce,
                signedMessage.signature,
                errorCode
        );
        //noinspection ConstantConditions
        if (errorCode != 0) {
            throw new TariCryptoException(
                    errorCode,
                    jniLookupErrorCode(errorCode)
            );
        }
        return verificationIsSuccessful;
    }

    /**
     * Hexadecimal string representation.
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return Util.bytesToHex(bytes);
    }

}
