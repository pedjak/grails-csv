class CsvTestController {

	def writeCsv = {
		def rows = []
		rows << [1,2]
		rows << [3,4]
		rows << [5,6]

		renderCsv(rows: rows, filename: params.filename, attachment: params.attachment) {
			x { it[0] }
			y { it[1] }
		}
	}

}