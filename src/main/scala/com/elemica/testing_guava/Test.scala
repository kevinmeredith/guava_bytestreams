package com.elemica.testing_guava

import com.google.common.io.ByteStreams
import java.io._
import org.xerial.snappy.{Snappy, SnappyInputStream}
import java.nio.charset.StandardCharsets
import scala.io.Source

object Test {
	
	val SNAPPY_COMPRESSED_FILE = 
	"/Users/kevinmeredith/Workspace/work/guava_bytestreams/src/main/scala/" +
		"com/elemica/testing_guava/001a8058_9250_493d_a94e_d28a369c75a6"

	val ENCODING = "UTF-8"

	// check if `Snappy.isValidCompressedBuffer` returns true for a snappy-compressed JSON
	def foo: Boolean = {
		val is: InputStream    = new FileInputStream(SNAPPY_COMPRESSED_FILE)
		println(s"file exists: ${new File(SNAPPY_COMPRESSED_FILE).exists}")
		//val snappyStream       = new SnappyInputStream(is)
		val bytes: Array[Byte] = ByteStreams.toByteArray(is)
		is.close
		Snappy.isValidCompressedBuffer(bytes)
	}

	// use the S3Storage#decompress to de-compress the Snappy-compressed file, then print its contents as a String
	def bar: String =  {
		val is: InputStream         = new FileInputStream(SNAPPY_COMPRESSED_FILE)
		decompress(is)

		// val xs: Array[Byte]         = decompressed.getBytes(ENCODING)
		// new String(xs, StandardCharsets.UTF_8)
	}

	// Use Snappy.uncompress to try to un-compress. Note that it's different than the approach of S3Storage, 
	// which uses `SnappyInputStream`. Finally, it uses the `Snappy#isValidCompressedBuffer` to see if it's compressed
	// def baz: String =  {
	// 	val is: InputStream           = new FileInputStream(SNAPPY_COMPRESSED_FILE)
	// 	val decompressed: Array[Byte] = uncompress(is)
	// 	Snappy.isValidCompressedBuffer(compressed)
	// }

	// use Snappy.uncompress to de-compress
    def uncompress: String = {
		val is: InputStream           = new FileInputStream(SNAPPY_COMPRESSED_FILE)
		val bytes: Array[Byte] 		  = ByteStreams.toByteArray(is)
		val uncompressed: Array[Byte] = Snappy.uncompress(bytes)
		new String(uncompressed, StandardCharsets.UTF_8)
	}

	// taken from S3Storage.scala in elemica's code
  	private def decompress(stream: InputStream): String = {
	    val snappyStream = new SnappyInputStream(stream)
	    val out = Source.fromInputStream(snappyStream, ENCODING).mkString
	    stream.close // AWS documentation recommends immediate closing
	    out
  	}

}