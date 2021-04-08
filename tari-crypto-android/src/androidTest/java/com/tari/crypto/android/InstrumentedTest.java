package com.tari.crypto.android;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tari.crypto.android.exception.TariCryptoException;
import com.tari.crypto.android.model.KeyPair;
import com.tari.crypto.android.model.SignedMessage;

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
        final KeyPair keyPair = KeyPair.generateRandom();
        assertNotNull(keyPair.privateKey);
        assertTrue(keyPair.privateKey.bytes.length > 0);
        assertNotNull(keyPair.publicKey);
        assertTrue(keyPair.publicKey.bytes.length > 0);
    }

    @Test
    public void testMessageSigning() {
        final KeyPair keyPair = KeyPair.generateRandom();
        final String message = "A random message.";
        final SignedMessage signedMessage = keyPair.privateKey.signMessage(message);
        assertNotNull(signedMessage.nonce);
        assertTrue(signedMessage.nonce.length > 0);
        assertNotNull(signedMessage.signature);
        assertTrue(signedMessage.signature.length > 0);
    }

    @Test
    public void testSignatureVerification() throws TariCryptoException {
        final KeyPair keyPair = KeyPair.generateRandom();
        final String message = "A random message.";
        final SignedMessage signedMessage = keyPair.privateKey.signMessage(message);
        assertTrue(keyPair.publicKey.verifySignature(signedMessage));
    }

    @Test
    public void testVerificationFailure() throws TariCryptoException  {
        final KeyPair keyPair = KeyPair.generateRandom();
        final String message = "Another random message.";
        final SignedMessage signedMessage = keyPair.publicKey.signMessage(message);
        assertFalse(keyPair.publicKey.verifySignature(signedMessage));
    }

}