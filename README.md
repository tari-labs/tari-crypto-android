# Tari Crypto Android

This Android module is a wrapper around the [Tari Crypto](https://github.com/tari-project/tari-crypto) library. It contains native libraries for different Android architectures, and the necessary JNI, wrapper and model classes to enable the developers to generate key pairs, sign messages and verify message signatures.

Please view [InstrumentedTest.java](tari-crypto-android/src/androidTest/java/com/tari/crypto/android/InstrumentedTest.java) for usage examples.

## Testing

- Clone the project with `git clone https://github.com/tari-labs/tari-crypto-android.git`.
- Open Android Studio, and open the project with `Open an Existing Project`.
- Start an emulator.
- Right click on the class file `com.tari.crypto.android.InstrumentedTest`, and create a run configuration for it.
- Run the class `com.tari.crypto.android.InstrumentedTest`, and the tests will be run on the emulator.

## Installation

_TODO_