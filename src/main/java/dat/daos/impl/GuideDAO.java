package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.GuideDTO;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;
import java.util.Set;

public class GuideDAO implements IDAO<GuideDTO> {

    private static GuideDAO instance;
    private static EntityManagerFactory emf;


    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }


    @Override
    public Set<GuideDTO> readAll() {
        return null;

    }

    @Override
    public Optional<GuideDTO> read(Long id) throws ApiException {
       try(EntityManager em = emf.createEntityManager()){
           GuideDTO guide = em.find(GuideDTO.class, id);

           if(guide == null){
               throw new ApiException(404, "Guide not found");
           }
           return Optional.of(guide);
       }
    }

    @Override
    public GuideDTO create(GuideDTO entity) {
        return null;
    }

    @Override
    public Optional<GuideDTO> update(Long id, GuideDTO entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
