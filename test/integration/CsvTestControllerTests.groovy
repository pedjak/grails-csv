import org.codehaus.groovy.grails.plugins.PluginManagerHolder

class CsvTestControllerTests extends GroovyTestCase {

	def grailsApplication
	
	void testRenderAsCsv() {
		def c = createController()
		c.writeCsv()
		def r = c.response
		
		assertEquals("text/csv", r.contentType)
		assertEquals('"x","y"\n"1","2"\n"3","4"\n"5","6"', r.contentAsString)
		assertNull(r.getHeader("Content-Disposition"))
	}
	
	void testRenderAsCsvAsAttachment() {
		def c = createController()
		c.params.filename = 'my.csv'
		c.writeCsv()
		def r = c.response
		assertEquals('attachment; filename="my.csv";', r.getHeader("Content-Disposition"))
	}

	void testRenderAsCsvAsInline() {
		def c = createController()
		c.params.filename = 'my.csv'
		c.params.attachment = false
		c.writeCsv()
		def r = c.response
		assertEquals('inline; filename="my.csv";', r.getHeader("Content-Disposition"))
	}

	void testRenderAsCsvAfterClassReload() {
		reloadControllerClass()
		createController().writeCsv() // no MME means success
	}

	protected createController() {
		grailsApplication.mainContext['CsvTestController']
	}
	
	protected reloadControllerClass() {
		def nc = grailsApplication.classLoader.reloadClass('CsvTestController')
		PluginManagerHolder.pluginManager.informOfClassChange(nc)
	}

}