package dat.daos.impl;

import dat.daos.IDAO;
import dat.daos.ITripGuideDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategory;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {
    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }
    // Metode til at populere databasen
    public void populateDatabase() throws ApiException {
        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();

            // Opret guides med phone som int
            Guide guide1 = new Guide(new GuideDTO("Alice", "Johnson", "alice.johnson@example.com", 123456789, 5));
            Guide guide2 = new Guide(new GuideDTO("Bob", "Smith", "bob.smith@example.com", 987654321, 10));

            // Tilføj guider til databasen
            em.persist(guide1);
            em.persist(guide2);

            // Opret trips med tilknyttede guides
            Trip beachTrip = new Trip(new TripDTO(LocalDate.now(), LocalDate.now().plusDays(2),
                    "Beach Resort", "Tropical Beach Adventure", 499.99, TripCategory.BEACH, new GuideDTO(guide1)));

            Trip mountainTrip = new Trip(new TripDTO(LocalDate.now().plusWeeks(1), LocalDate.now().plusWeeks(1).plusDays(2),
                    "Mountain Base", "Mountain Climbing Expedition", 799.99, TripCategory.SNOW, new GuideDTO(guide2)));

            // Opret trips uden tilknyttede guides
            Trip lakeTrip = new Trip(new TripDTO(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5),
                    "Lake Retreat", "Calm Lake Escape", 299.99, TripCategory.LAKE, null));

            Trip forestTrip = new Trip(new TripDTO(LocalDate.now().plusDays(4), LocalDate.now().plusDays(6),
                    "Forest Cabin", "Peaceful Forest Retreat", 399.99, TripCategory.FOREST, null));

            // Tildel guider til nogle af turene
            beachTrip.setGuide(guide1);  // Sæt guide1 som guide for beachTrip
            mountainTrip.setGuide(guide2);  // Sæt guide2 som guide for mountainTrip


            // Tilføj trips til guider
            guide1.getTrips().add(beachTrip);
            guide2.getTrips().add(mountainTrip);

            // Persist trips
            em.persist(beachTrip);
            em.persist(mountainTrip);
            em.persist(lakeTrip);
            em.persist(forestTrip);

            em.getTransaction().commit();


        }catch (PersistenceException e){
            throw new ApiException(400, "Something went wrong during populateDatabase");
        }finally {
            em.close();
        }
    }


    @Override
    public Set<TripDTO> readAll() throws ApiException {
       try(EntityManager em = emf.createEntityManager()) {
           TypedQuery<Trip>query = em.createQuery("SELECT t FROM Trip t", Trip.class);
              return query.getResultList().stream()
                     .map(TripDTO::new)
                     .collect(Collectors.toSet());
       }catch (PersistenceException e){
           throw new ApiException(400, "Something went wrong during readAll");
       }
    }

    @Override
    public Optional<TripDTO> read(Long id) throws ApiException {

        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null ? Optional.of(new TripDTO(trip)) : Optional.empty();
        }catch (PersistenceException e){
            throw new ApiException(400, "Something went wrong during read");
        }
    }

    @Override
    public TripDTO create(TripDTO entity) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(entity);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }catch (PersistenceException e){
            throw new ApiException(400, "Something went wrong during create");
        }
    }

    @Override
    public Optional<TripDTO> update(Long id, TripDTO updatedTripDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Find den eksisterende trip baseret på ID
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                return Optional.empty(); // Returner tomt Optional, hvis trip ikke findes
            }

            // Opdater felterne i den fundne trip
            trip.setStarttime(updatedTripDTO.getStarttime());
            trip.setEndtime(updatedTripDTO.getEndtime());
            trip.setStartposition(updatedTripDTO.getStartposition());
            trip.setName(updatedTripDTO.getName());
            trip.setPrice(updatedTripDTO.getPrice());
            trip.setCategory(updatedTripDTO.getCategory());

            if (updatedTripDTO.getGuide() != null) {
                Guide guide = em.find(Guide.class, updatedTripDTO.getGuide().getId());
                trip.setGuide(guide);
            } else {
                trip.setGuide(null);
            }

            // Gem ændringerne
            em.getTransaction().commit();

            // Returner den opdaterede TripDTO
            return Optional.of(new TripDTO(trip));
        }catch (PersistenceException e){
            throw new ApiException(400, "Something went wrong during update");
        }

    }

    @Override
    public void delete(Long id) throws ApiException {

        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new ApiException(404, "Trip with that id is not found");
            }
            em.remove(trip);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new ApiException(400, "Something went wrong during delete");
        }


    }

    @Override
    public void addGuideToTrip(Long tripId, Long guideId) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            if (trip == null) {
                throw new ApiException(404, "Trip with that id is not found");
            }
            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new ApiException(404, "Guide with that id is not found");
            }
            trip.setGuide(guide);
            guide.getTrips().add(trip);

            em.merge(trip);
            em.merge(guide);

            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new ApiException(400, "Something went wrong during addGuideToTrip");
        }

    }

    @Override
    public Set<TripDTO> getTripsByGuide(Long guideId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            // Find all trips associated with the specified guide
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t WHERE t.guide.id = :guideId", Trip.class);
            query.setParameter("guideId", guideId);
            return query.getResultList().stream().map(TripDTO::new).collect(Collectors.toSet());

        } catch (PersistenceException e) {
            throw new ApiException(400, "Error retrieving trips for guide with id: " + guideId);
        }
    }

    public Set<TripDTO> getTripsByCategory(TripCategory category) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Trip t WHERE t.category = :category", Trip.class)
                    .setParameter("category", category)
                    .getResultList()
                    .stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toSet());
        }catch (PersistenceException e){
            throw new ApiException(400, "Something went wrong during getTripsByCategory");
        }
    }


    public double getTotalPricePerGuide(Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT SUM(t.price) FROM Trip t WHERE t.guide.id = :guideId", Double.class)
                    .setParameter("guideId", guideId)
                    .getSingleResult();
        }
    }
}
