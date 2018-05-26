function doIt(param) {
   // var message = "hello world " + param.getName() + "!";

    var CarController = Java.type('com.daimler.controller.CarController')
    var id=param.getId();
    var car=CarController.lookup(id)

    return car;
}

