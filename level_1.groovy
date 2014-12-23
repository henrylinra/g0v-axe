@Grapes(
    @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2.1')
)

def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
def slurper = new XmlSlurper(tagsoupParser)
def htmlParser = slurper.parse("http://axe-level-1.herokuapp.com/")
