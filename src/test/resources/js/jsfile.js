function getId(param) {

    var CarController = Java.type('com.daimler.controller.CarController');

    var id= param.getId();
    var c = new CarController();
    var car=c.lookup(id);

    return car;
}

