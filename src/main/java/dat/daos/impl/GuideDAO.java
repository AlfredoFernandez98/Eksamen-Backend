package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.GuideDTO;

import java.util.Optional;
import java.util.Set;

public class GuideDAO implements IDAO<GuideDTO> {

    @Override
    public Set<GuideDTO> readAll() {
        return null;

    }

    @Override
    public Optional<GuideDTO> read(Long id) {
        return Optional.empty();
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
