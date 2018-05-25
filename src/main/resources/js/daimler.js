importClass(Packages.com.daimler.controller.CarController)
var id=request.getParameter("id")
var car=CarController.lookup("1")
console.log(car);
