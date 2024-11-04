package dat.config;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Populator {

    static EntityManagerFactory emf= HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {
        populateTrips(emf);
    }


    public static Trip[] populateTrips(EntityManagerFactory emf) {
        Set<Guide> beachGuides = getBeachGuides();
        Set<Guide> snowGuides = getSnowGuides();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip beachTrip = new Trip();
            beachTrip.setStarttime(LocalDate.now().plusDays(1));
            beachTrip.setEndtime(LocalDate.now().plusDays(2));
            beachTrip.setStartposition("Sunny Beach");
            beachTrip.setName("Relaxing Beach Day");
            beachTrip.setPrice(299.99);
            beachTrip.setCategory(TripCategory.BEACH);
            beachTrip.setGuide(beachGuides.iterator().next());  // Sæt en af beachGuides som guide

            Trip snowTrip = new Trip();
            snowTrip.setStarttime(LocalDate.now().plusWeeks(1));
            snowTrip.setEndtime(LocalDate.now().plusWeeks(1).plusDays(2));
            snowTrip.setStartposition("Snowy Mountain Base");
            snowTrip.setName("Mountain Snow Adventure");
            snowTrip.setPrice(599.99);
            snowTrip.setCategory(TripCategory.SNOW);
            snowTrip.setGuide(snowGuides.iterator().next());  // Sæt en af snowGuides som guide

            em.persist(beachTrip);
            em.persist(snowTrip);
            em.getTransaction().commit();

            return new Trip[]{beachTrip, snowTrip};

        } catch (Exception e) {
            e.printStackTrace();
            return new Trip[0];
        }
    }

    private static Set<Guide> getBeachGuides() {
        Guide guide1 = new Guide();
        guide1.setFirstname("Alice");
        guide1.setLastname("Johnson");
        guide1.setEmail("alice.johnson@beachguides.com");
        guide1.setPhone(11111111);  // Telefon som int
        guide1.setYearsOfExperience(3);

        Guide guide2 = new Guide();
        guide2.setFirstname("Bob");
        guide2.setLastname("Smith");
        guide2.setEmail("bob.smith@beachguides.com");
        guide2.setPhone(22222222);  // Telefon som int
        guide2.setYearsOfExperience(5);

        return Set.of(guide1, guide2);
    }

    private static Set<Guide> getSnowGuides() {
        Guide guide1 = new Guide();
        guide1.setFirstname("Charlie");
        guide1.setLastname("Brown");
        guide1.setEmail("charlie.brown@snowguides.com");
        guide1.setPhone(33333333);  // Telefon som int
        guide1.setYearsOfExperience(4);

        Guide guide2 = new Guide();
        guide2.setFirstname("Daisy");
        guide2.setLastname("Adams");
        guide2.setEmail("daisy.adams@snowguides.com");
        guide2.setPhone(44444444);  // Telefon som int
        guide2.setYearsOfExperience(6);

        return Set.of(guide1, guide2);
    }

}
