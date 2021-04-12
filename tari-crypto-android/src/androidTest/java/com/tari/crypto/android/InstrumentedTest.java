package com.tari.crypto.android;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tari.crypto.android.exception.TariCryptoException;
import com.tari.crypto.android.model.TariKey;
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
    public void testKeysFromHexString() throws TariCryptoException {
        final String privateKeyHexString =
                "487DCD68D98584F046D6FEE57002E31C52828D6A23BF5431783F851311967C00";
        final String publicKeyHexString =
                "72B780D73DE7CE6396CFC41AC7E8D723CCB4C23D4D93CD0B32D16F4800A1DA1A";
        final TariKey privateKey = new TariKey(privateKeyHexString);
        final TariKey publicKey = new TariKey(publicKeyHexString);
        assertEquals(privateKey.toString(), privateKeyHexString);
        assertEquals(publicKey.toString(), publicKeyHexString);
        final String message = "A random message.";
        final TariSignedMessage signedMessage = privateKey.signMessage(message);
        assertTrue(publicKey.verifySignature(signedMessage));
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
    public void testVerificationFailure() throws TariCryptoException {
        final TariKeyPair keyPair = TariKeyPair.generateRandom();
        final String message = "Another random message.";
        final TariSignedMessage signedMessage = keyPair.publicKey.signMessage(message);
        assertFalse(keyPair.publicKey.verifySignature(signedMessage));
    }

}