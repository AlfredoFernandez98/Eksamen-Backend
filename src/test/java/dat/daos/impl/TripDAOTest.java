package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategory;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TripDAOTest {

    private static EntityManagerFactory emf;
    private static TripDAO dao;
    private static Trip testTrip1, testTrip2, testTrip3, testTrip4, testTrip5;
    private static Guide testGuide;


    @BeforeAll
    static void setUpClass() {
        // Use test configuration for Hibernate
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = TripDAO.getInstance(emf);
    }
    @AfterAll
    static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }
    @BeforeEach
    void setUp() {
        // Clear database and create test data
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.createQuery("DELETE FROM Guide").executeUpdate();

            // Create test guide
            testGuide = new Guide(new GuideDTO("Alice", "Johnson", "alice.johnson@example.com", 123456789, 5));
            em.persist(testGuide);

            // Create test trips
            testTrip1 = new Trip(new TripDTO(LocalDate.now(), LocalDate.now().plusDays(2), "Beach Resort", "Beach Adventure", 499.99, TripCategory.BEACH, new GuideDTO(testGuide)));
            testTrip2 = new Trip(new TripDTO(LocalDate.now().plusWeeks(1), LocalDate.now().plusWeeks(1).plusDays(2), "Mountain Base", "Mountain Climbing", 799.99, TripCategory.SNOW, new GuideDTO(testGuide)));
            testTrip3 = new Trip(new TripDTO(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5),
                    "Lake Retreat", "Calm Lake Escape", 299.99, TripCategory.LAKE, null));
             testTrip4 = new Trip(new TripDTO(LocalDate.now().plusDays(4), LocalDate.now().plusDays(6),
                    "Forest Cabin", "Peaceful Forest Retreat", 399.99, TripCategory.FOREST, null));
            testTrip5 = new Trip(new TripDTO(LocalDate.now().plusDays(7), LocalDate.now().plusDays(9),
                    "Desert Oasis", "Desert Safari Adventure", 599.99, TripCategory.SNOW, new GuideDTO(testGuide)));
            em.persist(testTrip1);
            em.persist(testTrip2);
            em.persist(testTrip3);
            em.persist(testTrip4);
            em.persist(testTrip5);

            em.getTransaction().commit();
        }
    }


    @Test
    @DisplayName("Test reading all trips")
    void readAll() throws ApiException {
        Set<TripDTO> trips = dao.readAll();
        assertEquals(5, trips.size());
    }

    @Test
    @DisplayName("Read trip by id")
    void read() throws ApiException {
        // Læs testTrip1 baseret på ID
        Optional<TripDTO> found = dao.read(testTrip1.getId());

        // Assert: Kontroller, at trip blev fundet og har de forventede værdier
        assertTrue(found.isPresent());
        assertEquals("Beach Adventure", found.get().getName());
        assertEquals(TripCategory.BEACH, found.get().getCategory());

    }

    @Test
    @DisplayName("create trip")
    void create() throws ApiException {
        // Opret en ny TripDTO
        TripDTO newTrip = new TripDTO(LocalDate.now().plusDays(10), LocalDate.now().plusDays(12),
                "River Camp", "Riverside Adventure", 450.99, TripCategory.SEA, null);

        // Act: Skab trip i databasen
        TripDTO created = dao.create(newTrip);

        // Assert: Kontroller, at trip blev skabt med de forventede værdier
        assertNotNull(created);
        assertNotNull(created.getId()); // ID skal genereres ved oprettelse

    }

    @Test
    @DisplayName("update trip")
    void update() throws ApiException {
        // Opret en ny TripDTO med ændrede værdier
        TripDTO updatedTrip = new TripDTO(testTrip1.getId(), LocalDate.now(), LocalDate.now().plusDays(3),
                "Updated Beach Resort", "Updated Beach Adventure", 599.99, TripCategory.BEACH, new GuideDTO(testGuide));

        // Act: Opdater trip i databasen
        Optional<TripDTO> updated = dao.update(testTrip1.getId(), updatedTrip);

        // Assert: Kontroller, at trip blev opdateret med de nye værdier
        assertTrue(updated.isPresent());

    }

    @Test
    @DisplayName("delete trip")
    void delete() throws ApiException {
        // Act: Slet trip fra databasen
        dao.delete(testTrip1.getId());

        // Assert: Kontroller, at trip ikke længere findes i databasen
        assertEquals(4, dao.readAll().size());
    }
}