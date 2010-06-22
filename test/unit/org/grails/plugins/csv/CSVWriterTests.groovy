package org.grails.plugins.csv

class CSVWriterTests extends GroovyTestCase {

	void testBuilderSimple() {
		assertCsv('"a","b"\n"1","2"', [[1,2]]) {
			a { it[0] }
			b { it[1] }
		}
	}

	void testBuilderQuoting() {
		assertCsv('"a""a""a","b""b""b"\n"1""1""1","2""2""2"', [['1"1"1','2"2"2']]) {
			'a"a"a' { it[0] }
			'b"b"b' { it[1] }
		}
	}

	void testBuilderMultipleRowsSingleColumn() {
		assertCsv('"a"\n"1"\n"1"\n"1"', [1, 1, 1]) {
			'a' { it }
		}
	}

	void testLeftShift() {
		def b = builder {
			a { it }
		}

		b << 1
		assertEquals('"a"\n"1"', b.writer.toString())
		b << 2
		assertEquals('"a"\n"1"\n"2"', b.writer.toString())
	}

	protected assertCsv(expected, rows, definition) {
		assertEquals(expected, builder(rows, definition))
	}

	protected builder(data, definition) {
		def builder = builder(definition)
		builder.writeAll(data).toString()
	}

	protected builder(definition) {
		def builder = new CSVWriter(new StringWriter(), definition)
	}
}