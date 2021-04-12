# Tari Crypto Android

This Android module is a wrapper around the [Tari Crypto](https://github.com/tari-project/tari-crypto) library. It contains native libraries for different Android architectures, and the necessary JNI, wrapper and model classes to enable the developers to generate key pairs, sign messages and verify message signatures.

## Installation

_TODO_

## Usage

Sample program that illustrates message signing and verification:

```kotlin
import com.tari.crypto.android.TariCrypto
import com.tari.crypto.android.model.TariKey
import com.tari.crypto.android.model.TariKeyPair

TariCrypto.init() // this initialization is mandatory
// test random key pair generation, signing and verification
val keyPair = TariKeyPair.generateRandom()
val message = "A random message."
val signedMessage = keyPair.privateKey.signMessage(message)
var isVerified = keyPair.publicKey.verifySignature(signedMessage)
println("Is first signature verified? $isVerified")

// test key generation from hex string
val privateKeyFromHex =
    TariKey("487DCD68D98584F046D6FEE57002E31C52828D6A23BF5431783F851311967C00")
val publicKeyFromHex =
    TariKey("72B780D73DE7CE6396CFC41AC7E8D723CCB4C23D4D93CD0B32D16F4800A1DA1A")
val anotherMessage = "Another random message."
val anotherSignedMessage = privateKeyFromHex.signMessage(anotherMessage)
isVerified = publicKeyFromHex.verifySignature(anotherSignedMessage)
println("Is second signature verified? $isVerified")
```

Program output:

```
Is first signature verified? true
Is second signature verified? true
```

## Testing

- Clone the project with `git clone https://github.com/tari-labs/tari-crypto-android.git`.
- Open Android Studio, and open the project with `Open an Existing Project`.
- Start an emulator or connect an Android device.
- Right click on the class file `com.tari.crypto.android.InstrumentedTest`, and create a run configuration for it.
- Run the class `com.tari.crypto.android.InstrumentedTest`, and the tests will be run on the emulator or device.