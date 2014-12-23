@Grapes(
    @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2.1')
)

def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
def slurper = new XmlSlurper(tagsoupParser)
def htmlParser = slurper.parse("http://axe-level-1.herokuapp.com/")

def trList = htmlParser.body.table.tr
def title = trList[0]
def result = []
trList[1..-1].each { trItem ->
	def row = [:] 
    row["name"] = trItem.td[0]

   	def grades = [:]
    (1..5).each { i ->
    	grades[title.td[i]]  = trItem.td[i]
    }
    row["grades"] = grades

    result << row
}

println result