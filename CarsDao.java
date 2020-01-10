package cardb_dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsDao implements Dao {

    private Connection connection;
    private Statement statement;


    @Override
    public void open() throws SQLException {
        initConnection();
        createTable();
    }

    @Override
    public void saveCar(Car car) throws SQLException {
        final String SQL_INSERT = String.format(
                "INSERT INTO cars (vin, brand, model, production_date, color) " +
                        "VALUES (%d, \"%s\", \"%s\", \"%s\", \"%s\")",
                car.getVin(),
                car.getBrand(),
                car.getModel(),
                car.getProductionDate("yyyy-MM-dd"),
                car.getColor());
        statement.executeUpdate(SQL_INSERT);
    }

    @Override
    public void updateCar(Car car) throws SQLException {

        final String SQL_UPDATE = String.format(
                "UPDATE cars SET " +
                        "vin = %d, " +
                        "brand = \"%s\", " +
                        "model = \"%s\", " +
                        "production_date = '%s', " +
                        "color = \"%s\" " +
                        "WHERE id = %d",
                car.getVin(),
                car.getBrand(),
                car.getModel(),
                car.getProductionDate("yyyy-MM-dd"),
                car.getColor(),
                car.getId()
        );

        statement.executeUpdate(SQL_UPDATE);

    }

    @Override
    public void deleteCar(int id) throws SQLException {

        final String SQL_REMOVE = String.format("DELETE FROM cars WHERE id = %d", id);
        statement.executeUpdate(SQL_REMOVE);

    }

    @Override
    public List<Car> getCars() throws SQLException {
        final String SQL_SELECT = "SELECT * FROM cars";
        ResultSet resultSet = statement.executeQuery(SQL_SELECT);
        List<Car> cars = new ArrayList<>();

        while (resultSet.next()) {
            Car car = new Car();

            car.setId(resultSet.getInt("id"));
            car.setVin(resultSet.getInt("vin"));
            car.setBrand(resultSet.getString("brand"));
            car.setModel(resultSet.getString("model"));
            car.setProductionDate(resultSet.getString("production_date"));
            car.setColor(resultSet.getString("color"));

            cars.add(car);
        }

        return cars;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        statement.close();
    }

    private void initConnection() throws SQLException {
        final String HOST = "localhost";
        final int PORT = 3306;
        final String DB_NAME = "sampledb";
        final String USER_NAME = "Test";
        final String PASSWORD = "Magda123";
        final String QUERY_STRING = "serverTimezone=UTC";
        String dburl = String.format("jdbc:mysql://%s:%d/%s?%s",
                HOST, PORT, DB_NAME, QUERY_STRING);

        connection = DriverManager.getConnection(dburl, USER_NAME, PASSWORD);

        DatabaseMetaData dbmd = connection.getMetaData();
        //System.out.printf("Połączenie nawiązane poprzez: %s%n", dbmd.getDriverName());

        statement = connection.createStatement();
    }

    private void createTable() throws SQLException {

        final String SQL_CREATE = "CREATE TABLE cars (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "vin INT NOT NULL," +
                "brand VARCHAR(255) NOT NULL," +
                "model VARCHAR(255) NOT NULL," +
                "production_date DATE NOT NULL," +
                "color VARCHAR(255)," +
                "PRIMARY KEY (id))";
        try {
            statement.executeUpdate(SQL_CREATE);
            //System.out.println("Utworzono tabelę.");
        } catch (SQLSyntaxErrorException e) {
            //System.out.println("Tabela cars już istnieje w bazie.");
        }
    }

}
