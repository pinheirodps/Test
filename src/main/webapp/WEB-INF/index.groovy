import com.daimler.controller.CarController

def cltr = new CarController()
def id = res.getParameter("id")
def car = cltr.lookup(id)
def htmlReturn = ""

def hashmap = car.getModels()
def writer = new StringWriter()  // html is written here by markup builder
def markup = new groovy.xml.MarkupBuilder(writer)  // the builder
String ecoFriendly = String.valueOf(car.isEcoFriendly())
String visible = car.isEcoFriendly() ?  "display:block":"display:none"

markup.html {
    head{
        title car.getBrand()
    }
    body {
        h1(title:car.getBrand(),  car.getBrand()){ }
        h2("data-if":ecoFriendly,style:visible, title:car.getBrand(),  'Fuel Type: '+car.getBrand()){ }
        for (item in hashmap.collect()) {
            div("Model: " + item) {}
        }
    }
}
htmlReturn = new String(writer.toString())
//println writer.toString()

