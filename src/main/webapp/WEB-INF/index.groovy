import com.daimler.controller.CarController

def cltr = new CarController()
def id = res.getParameter("id")

def car = cltr.lookup(id)

