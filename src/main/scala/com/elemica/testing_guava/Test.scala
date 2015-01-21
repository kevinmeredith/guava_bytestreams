package com.elemica.testing_guava

import com.google.common.io.ByteStreams
import java.io._
import org.xerial.snappy.{Snappy, SnappyInputStream}

object Test {
	
	val SNAPPY_COMPRESSED_FILE = 
	"/Users/kevinmeredith/Workspace/work/guava_bytestreams/src/main/scala/" +
		"com/elemica/testing_guava/001a8058_9250_493d_a94e_d28a369c75a6"

	def foo: Boolean = {
		println(s"file exists: ${new File(SNAPPY_COMPRESSED_FILE).exists}")
		val is: InputStream    = new FileInputStream(SNAPPY_COMPRESSED_FILE)
		val snappyStream       = new SnappyInputStream(is)
		val bytes: Array[Byte] = ByteStreams.toByteArray(snappyStream)
		Snappy.isValidCompressedBuffer(bytes)
	}

}