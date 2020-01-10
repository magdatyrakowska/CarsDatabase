package cardb_dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao {

    void open() throws SQLException;

    void saveCar(Car car) throws SQLException;

    void updateCar(Car car) throws SQLException;

    void deleteCar(int id) throws SQLException;

    List<Car> getCars() throws SQLException;

    void close() throws SQLException;

}
