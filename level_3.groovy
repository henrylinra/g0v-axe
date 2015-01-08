@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
@Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2.1')

import groovyx.net.http.HTTPBuilder
import org.ccil.cowan.tagsoup.Parser
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT

def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
slurper = new XmlSlurper(tagsoupParser)
result = []

def getPageData(html){
    def htmlParser = slurper.parseText(html)
    htmlParser.body.table.tr[1..-1].each { trItem ->
        def row = [:]
        row["town"] = trItem.td[0]
        row["village"] = trItem.td[1]
        row["name"] = trItem.td[2]
        result << row
    }
}

def main(){
	def	url = "http://axe-level-1.herokuapp.com/lv3/"
	http = new HTTPBuilder(url)
	http.request(GET, TEXT) { req ->
		def	nextUrl = "http://axe-level-1.herokuapp.com/lv3/?page=next"
		def httpNext = new HTTPBuilder(nextUrl)
		response.success = { resp, reader ->
			getPageData(reader.getText())
			75.times {
				httpNext.request(GET, TEXT) { reqNext ->
					response.success = {respNext, readerNext ->
						getPageData(readerNext.getText());
					}
				}
			}
		}
	}
}

main()
println result
