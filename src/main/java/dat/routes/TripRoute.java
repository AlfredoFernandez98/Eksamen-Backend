package dat.routes;

import dat.controllers.impl.TripController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoute {
    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes() {
        return () -> {

            get("/", tripController::readAll);
            get("/{id}", tripController::read);
            post("/create", tripController::create);
            put("/update/{id}", tripController::update);
            put("/addguide/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
            post("/populate", tripController::populateDatabase);
            delete("/delete/{id}", tripController::delete);
            put("/category", tripController::getTripsByCategory);

        };
    }
}
