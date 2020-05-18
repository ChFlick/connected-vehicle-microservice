package de.flick

import de.flick.traffic.BusesControllerTest
import io.quarkus.test.junit.NativeImageTest

@NativeImageTest
class NativeExampleResourceIT : BusesControllerTest()
