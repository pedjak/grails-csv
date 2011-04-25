package org.grails.plugins.csv

import grails.test.GrailsUnitTestCase
import org.springframework.core.io.Resource
import org.springframework.core.io.ClassPathResource

class CSVMapReaderIntTests extends GroovyTestCase {

	void testFileToCsvMapReader() {
		def recs = new File("test/resources/mapTest.csv").toCsvMapReader().toList()
		assert recs.size()==2
		assertEquals([col1:'val1',col2:'val2',col3:'val3'], recs[0])
    }
}
