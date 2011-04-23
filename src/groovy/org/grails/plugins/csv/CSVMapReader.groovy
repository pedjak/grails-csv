/*
 * Copyright 2010 Joshua Burnett
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

import au.com.bytecode.opencsv.CSVParser
import au.com.bytecode.opencsv.CSVReader

/**
 * Class the read csv and return the rows as a map assuming the first row has the field/key names or you explicitly set fieldKeys property
 * Uses an underlying OpenCSV's CSVReader
 *
 * @since 0.2
 * @author Joshua Burnett
 */
public class CSVMapReader implements Closeable{

    CSVReader csvReader

	def fieldKeys = []
	
	public CSVMapReader() {
    }

    /**
     * Constructs CSVMapReader from the reader
     *
     * @param reader the reader to an underlying CSV source.
     */
	public CSVMapReader(Reader reader) {
		this(reader, null)
    }

    /**
     * Constructs CSVMapReader from the reader
     *
     * @param reader    the reader to an underlying CSV source.
     * @param settingsMap map of settings for the underlying CSVReader.
     */
    public CSVMapReader(Reader reader, Map settingsMap) {
		csvReader = CSVReaderUtils.toCsvReader(reader,settingsMap) 
    }

	void each(Closure closure){
		try {
			fieldKeys = fieldKeys?:csvReader.readNext()
            String[] tokenArray = csvReader.readNext()
            while (tokenArray) {
                closure(convertArrayToMap(fieldKeys,tokenArray))
                tokenArray = csvReader.readNext()
            }
        } finally {
            csvReader.close()
        }
	}
	
	void eachLine(Closure closure){
		each(closure)
	}
	
	/**
     * Reads the entire file into a List with each element being a map where keys are the from the 1st header line in file
     *
     * @return a List of Maps with each map representing a line of the
     *         file.
     * @throws IOException if bad things happen during the read
     */
    public List<Map> readAll() throws IOException {

        def list = []
        eachLine{ map ->
			list.add map
		}
		return list
    }

	/**
     * a more groovy alternate to readAll (opencsv syntax) that returns this as a list of maps
     */
    List toList(){
		return readAll()
    }

	//groovy way to cast, can do something like " def list = new CSVMapReader(file) as List
	Object asType(Class type){
		if(type == List) return readAll()
	}
	
	
	static Map convertArrayToMap(keys,tokens){
		def map = [:]
		for ( i in 0..tokens.size()-1 ) {
			map[keys[i]] = tokens[i]
		}
		return map	
	}
	
	/**
     * Just calls close on CSVReader and closes the underlying reader.
     */
    public void close() throws IOException {
        csvReader.close();
    }


}
