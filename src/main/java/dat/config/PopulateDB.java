package dat.config;

import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;


public class PopulateDB {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

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
        em.close();
    }
}