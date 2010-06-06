/*
 * Copyright 2010 Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
package org.grails.plugins.csv

import grails.test.GrailsUnitTestCase
import org.springframework.core.io.Resource
import org.springframework.core.io.ClassPathResource

/**
 * Tests to verify dynamically added methods to Java/Groovy classes (String, InputStream, etc) work as expected
 * 
 * @since 0.1
 * @author Les Hazlewood
 */
class CSVReaderUtilsIntegrationTest extends GrailsUnitTestCase {

    void testFileEachCsvLine() {
        Resource resource = new ClassPathResource("org/grails/plugins/csv/fileTest.csv")
        def file = resource.getFile()
        assertNotNull(file)
        assertTrue(file.exists())

        file.eachCsvLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    void testFileEachCsvLineWithSettings() {
        Resource resource = new ClassPathResource("org/grails/plugins/csv/fileTest.csv")
        def file = resource.getFile()
        assertNotNull(file)
        assertTrue(file.exists())

        file.toCsvReader(['charset':'UTF-8']).eachLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    void testInputStreamEachCsvLine() {
        Resource resource = new ClassPathResource("org/grails/plugins/csv/fileTest.csv")
        def is = resource.getInputStream()
        assertNotNull(is)

        is.eachCsvLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    void testInputStreamEachCsvLineWithSettings() {
        Resource resource = new ClassPathResource("org/grails/plugins/csv/fileTest.csv")
        def is = resource.getInputStream()
        assertNotNull(is)

        is.toCsvReader(['charset':'UTF-8']).eachLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    void testReaderEachCsvLine() {
        Resource resource = new ClassPathResource("org/grails/plugins/csv/fileTest.csv")
        def reader = new FileReader(resource.getFile())

        reader.eachCsvLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    void testReaderEachCsvLineWithSettings() {
        Resource resource = new ClassPathResource("org/grails/plugins/csv/fileTest.csv")
        def reader = new FileReader(resource.getFile())

        reader.toCsvReader(['charset':'UTF-8']).eachLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    void testStringEachCsvLine() {
        String csv = 'test, with, "nested, commas", and a, "multiline, \n comma-delimited, \n value"'

        csv.eachCsvLine { tokens ->
            assertEquals(5, tokens.length)
        }
    }

    void testStringEachCsvLineWithSettings() {
        String csv = 'testx using "x" as ax separator char'

        csv.toCsvReader(['separatorChar': 'x']).eachLine { tokens ->
            assertEquals(3, tokens.length)
        }
    }

}
