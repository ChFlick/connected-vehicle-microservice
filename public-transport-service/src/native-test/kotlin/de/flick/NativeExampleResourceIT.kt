package de.flick

import de.flick.traffic.TrafficControllerTest
import io.quarkus.test.junit.NativeImageTest

@NativeImageTest
class NativeExampleResourceIT : TrafficControllerTest()
