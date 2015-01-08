@Grapes(
    @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2.1')
)

def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
def slurper = new XmlSlurper(tagsoupParser)
result = []  // Global scope

def getItems(trList) {
	trList[1..-1].each {
		def row = [:] 
		row["town"] = it.td[0]
		row["village"] = it.td[1]
		row["name"] = it.td[2]

		result << row
	}
}

def pages = (1..12)
pages.each {
	def url = "http://axe-level-1.herokuapp.com/lv2/?page=${it}"
	def htmlParser = slurper.parse(url)
	def trList = htmlParser.body.table.tr
	getItems(trList)
}

println result
