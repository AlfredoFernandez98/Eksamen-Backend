package dat.daos;



import dat.exceptions.ApiException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface IDAO<T> {
    Set<T> readAll() throws ApiException;
    Optional<T> read(Long id) throws ApiException;
    T create(T entity) throws ApiException;
    Optional<T> update(Long id, T entity) throws ApiException;
    void delete(Long id) throws ApiException;
}
