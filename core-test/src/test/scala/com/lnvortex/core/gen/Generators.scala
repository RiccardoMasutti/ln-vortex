package com.lnvortex.core.gen

import com.lnvortex.core._
import org.bitcoins.testkitcore.gen._
import org.scalacheck.Gen

object Generators {

  def mixAdvertisement: Gen[MixAdvertisement] = {
    for {
      amount <- CurrencyUnitGenerator.positiveSatoshis
      pubkey <- CryptoGenerators.schnorrPublicKey
      nonce <- CryptoGenerators.schnorrNonce
    } yield {
      MixAdvertisement(amount, pubkey, nonce)
    }
  }

  def aliceInit: Gen[AliceInit] = {
    for {
      numInputs <- Gen.choose(1, 7)
      inputs <- Gen.listOfN(numInputs, TransactionGenerators.outputReference)
      blindedOutput <- CryptoGenerators.fieldElement
      changeOutput <- TransactionGenerators.output
    } yield AliceInit(inputs.toVector, blindedOutput, changeOutput)
  }

  def aliceInitResponse: Gen[AliceInitResponse] = {
    for {
      blindOutputSig <- CryptoGenerators.fieldElement
    } yield AliceInitResponse(blindOutputSig)
  }

  def bobMessage: Gen[BobMessage] = {
    for {
      sig <- CryptoGenerators.schnorrDigitalSignature
      output <- TransactionGenerators.output
    } yield BobMessage(sig, output)
  }

  def unsignedPsbtMessage: Gen[UnsignedPsbtMessage] = {
    for {
      psbt <- PSBTGenerators.arbitraryPSBT
    } yield UnsignedPsbtMessage(psbt)
  }

  def signedPsbtMessage: Gen[SignedPsbtMessage] = {
    for {
      psbt <- PSBTGenerators.arbitraryPSBT
    } yield SignedPsbtMessage(psbt)
  }

  def signedTxMessage: Gen[SignedTxMessage] = {
    for {
      tx <- TransactionGenerators.transaction
    } yield SignedTxMessage(tx)
  }
}
