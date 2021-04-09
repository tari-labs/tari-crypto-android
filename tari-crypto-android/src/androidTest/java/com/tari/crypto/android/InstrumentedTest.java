package com.tari.crypto.android;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tari.crypto.android.exception.TariCryptoException;
import com.tari.crypto.android.model.TariKeyPair;
import com.tari.crypto.android.model.TariSignedMessage;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    @BeforeClass
    public static void loadLibrary() {
        TariCrypto.init();
    }

    @Test
    public void testRandomKeyPairGeneration() {
        final TariKeyPair keyPair = TariKeyPair.generateRandom();
        assertNotNull(keyPair.privateKey);
        assertTrue(keyPair.privateKey.bytes.length > 0);
        assertNotNull(keyPair.publicKey);
        assertTrue(keyPair.publicKey.bytes.length > 0);
        Log.d(
            TariCrypto.LOG_TAG,
            "Successfully generated key pair: " + keyPair
        );
    }

    @Test
    public void testMessageSigning() {
        final TariKeyPair keyPair = TariKeyPair.generateRandom();
        final String message = "A random message.";
        final TariSignedMessage signedMessage = keyPair.privateKey.signMessage(message);
        assertNotNull(signedMessage.nonce);
        assertTrue(signedMessage.nonce.length > 0);
        assertNotNull(signedMessage.signature);
        assertTrue(signedMessage.signature.length > 0);
        Log.d(
                TariCrypto.LOG_TAG,
                "Successfully signed message: " + signedMessage
        );
    }

    @Test
    public void testSignatureVerification() throws TariCryptoException {
        final TariKeyPair keyPair = TariKeyPair.generateRandom();
        final String message = "A random message.";
        final TariSignedMessage signedMessage = keyPair.privateKey.signMessage(message);
        assertTrue(keyPair.publicKey.verifySignature(signedMessage));
    }

    @Test
    public void testVerificationFailure() throws TariCryptoException  {
        final TariKeyPair keyPair = TariKeyPair.generateRandom();
        final String message = "Another random message.";
        final TariSignedMessage signedMessage = keyPair.publicKey.signMessage(message);
        assertFalse(keyPair.publicKey.verifySignature(signedMessage));
    }

}