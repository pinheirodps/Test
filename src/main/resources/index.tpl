
    <script type="server/groovy">
        import com.daimler.controller.CarController
        def cltr = new CarController()
        def id = res.getParameter("id")
        def car = cltr.lookup(id)
</script>
<!DOCTYPE html>
    <html>
    <head>
        <title>${car.brand}</title>
    </head>
    <body>
        <h1 title="${car.brand}">${car.brand}</h1>
        <h2 data-if="car.ecoFriendly" title="${car.fuelType}">Fuel Type:${car.fuelType}</h2>
        <div data-loop-model="car.models">Model:</div>
    </body>
</html>
