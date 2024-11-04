package dat.controllers.impl;

import dat.config.HibernateConfig;

import dat.controllers.IController;
import dat.daos.impl.TripDAO;
import dat.dtos.TripDTO;
import dat.enums.TripCategory;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class TripController implements IController<TripDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final TripDAO dao;

    public TripController(){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao =TripDAO.getInstance(emf);
    }

    /**
     * Retrieves a specific Trip by their ID
     *
     * @param ctx Javalin context containing the ID parameter
     * @throws ApiException with 404 if doctor not found
     */
    @Override
    public void read(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDTO tripDTO = dao.read(id)
                    .orElseThrow(() -> new ApiException(404,"Invalied ID: Trip not found"));
            ctx.json(tripDTO);

            ctx.status(200);

        }catch (NumberFormatException e) {
            LOGGER.error("Invalid ID format: {}", e.getMessage());
            ctx.status(404);
            ctx.json(new ApiException(404, "Invalid ID format"));
        } catch (ApiException e) {
            LOGGER.error("API Error in read: {}", e.getMessage());
            ctx.status(e.getStatusCode());
            ctx.json(new ApiException(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in read", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }

    }

    @Override
    public void readAll(Context ctx) {
        try {
            Set<TripDTO> tripDTOs = dao.readAll();
            ctx.status(200);
            ctx.json(tripDTOs, TripDTO.class);
        } catch (ApiException e) {
            LOGGER.error("API Error in readAll: {}", e.getMessage());
            ctx.status(e.getStatusCode());
            ctx.json(new ApiException(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in readAll", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }

    }

    @Override
    public void create(Context ctx) {
        try {
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
            TripDTO createdTrip = dao.create(tripDTO);
            ctx.status(201);
            ctx.json(createdTrip, TripDTO.class);
        } catch (ApiException e) {
            LOGGER.error("API Error in create: {}", e.getMessage());
            ctx.status(e.getStatusCode());
            ctx.json(new ApiException(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in create", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }

    }

    @Override
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
            TripDTO updatedTrip = dao.update(id, tripDTO)
                    .orElseThrow(() -> new ApiException(404, "Trip not found"));
            ctx.status(200);
            ctx.json(updatedTrip);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid ID format: {}", e.getMessage());
            ctx.status(404);
            ctx.json(new ApiException(404, "Invalid ID format"));
        } catch (ApiException e) {
            LOGGER.error("API Error in update: {}", e.getMessage());
            ctx.status(e.getStatusCode());
            ctx.json(new ApiException(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in update", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }

    }

    @Override
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            dao.delete(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid ID format: {}", e.getMessage());
            ctx.status(404);
            ctx.json(new ApiException(404, "Invalid ID format"));
        } catch (ApiException e) {
            LOGGER.error("API Error in delete: {}", e.getMessage());
            ctx.status(e.getStatusCode());
            ctx.json(new ApiException(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in delete", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }

    }

   public void addGuideToTrip(Context ctx){
        try{
            Long tripId = Long.parseLong(ctx.pathParam("tripId"));
            Long guideId = Long.parseLong(ctx.pathParam("guideId"));
            dao.addGuideToTrip(tripId, guideId);
            ctx.status(204);


        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

   }
    public void getTripsByGuide(Context ctx) {

        try {
            Long guideId = Long.parseLong(ctx.pathParam("guideId"));
            Set<TripDTO> trips = dao.getTripsByGuide(guideId);
            ctx.status(200);
            ctx.json(trips);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid ID format: {}", e.getMessage());
            ctx.status(404);
            ctx.json(new ApiException(404, "Invalid ID format"));
        } catch (ApiException e) {
            LOGGER.error("API Error in getTripsByGuide: {}", e.getMessage());
            ctx.status(e.getStatusCode());
            ctx.json(new ApiException(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in getTripsByGuide", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }


    }

    // Metode til at populere databasen
    public void populateDatabase(Context ctx) throws ApiException {
        dao.populateDatabase();
        ctx.status(201).result("Database populated with sample trips and guides.");
    }

    public void getTripsByCategory(Context ctx) {
        try {
            String categoryParam = ctx.pathParam("category").toUpperCase();
            Set<TripDTO> trips = dao.getTripsByCategory(TripCategory.valueOf(categoryParam));
            ctx.status(200);
            ctx.json(trips, TripDTO.class);
        } catch (Exception e) {
            LOGGER.error("Unexpected error in getTripsByCategory", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }
    }

    public void getTotalPricePerGuide(Context ctx) {
        try {
            Long guideId = Long.parseLong(ctx.pathParam("guideId"));
            double totalPrice = dao.getTotalPricePerGuide(guideId);
            ctx.status(200);
            ctx.result(String.valueOf(totalPrice));
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid ID format: {}", e.getMessage());
            ctx.status(404);
            ctx.json(new ApiException(404, "Invalid ID format"));
        } catch (Exception e) {
            LOGGER.error("Unexpected error in getTotalPricePerGuide", e);
            ctx.status(500);
            ctx.json(new ApiException(500, "Internal server error"));
        }
    }

}
