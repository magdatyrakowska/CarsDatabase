package cardb_dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class JdbcMain {


    public static void main(String[] args) {

        String option = "5";

        do {
            showMenu();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                option = reader.readLine();
            } catch (IOException e) {
                System.out.println("Podano niewłaściwą opcję");
                e.printStackTrace();
            }


            switch (option) {
                case "1":
                    addCar();
                    break;

                case "2":
                    editCar();
                    break;

                case "3":
                    deleteCar();
                    break;

                case "4":
                    showCars();
                    break;

                case "5":
                    break;

                default:
                    System.out.println("Nie wybrano żadnej z opcji");
            }
        }
        while (!option.equals("5"));

    }


    private static void showCars() {
        Dao carsDao = createDao();
        try {
            for (Car c : carsDao.getCars()) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            System.out.println("Błąd bazy danych");
            e.printStackTrace();
        }

        closeDao(carsDao);
    }


    private static void addCar() {
        Dao carsDao = createDao();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Car tmpCar = new Car();
        boolean addMoreCars = true;

        while (addMoreCars) {
            try {
                System.out.println("Podaj VIN ? ");
                tmpCar.setVin(Integer.parseInt(reader.readLine()));

                System.out.println("Podaj markę ?");
                tmpCar.setBrand(reader.readLine());

                System.out.println("Podaj model ?");
                tmpCar.setModel(reader.readLine());

                System.out.println("Podaj datę produkcji w formacie dd/mm/yyyy ?");
                String tmpProductionDate = reader.readLine();
                tmpCar.setProductionDateYear(Integer.parseInt(
                        tmpProductionDate.substring(6)));
                tmpCar.setProductionDateMonth(Integer.parseInt(
                        tmpProductionDate.substring(3, 5)));
                tmpCar.setProductionDateDay(Integer.parseInt(
                        tmpProductionDate.substring(0, 2)));

                System.out.println("Podaj kolor ?");
                tmpCar.setColor(reader.readLine());


            } catch (IOException e) {
                System.out.println("Wczytano błędne dane");
            }

            try {
                carsDao.saveCar(tmpCar);
                System.out.println("Samochód został dodany do bazy");
            } catch (SQLException e) {
                System.out.println("Dodanie pojazdów się nie powiodło, błąd przekazanych danych.");
                e.printStackTrace();
            }

            System.out.println("czy dodać kolejny samochód? T/N");
            try {
                addMoreCars = reader.readLine().equals("T");
            } catch (IOException e) {
                System.out.println("Podao błędną opcję");
                e.printStackTrace();
            }

        }

        closeDao(carsDao);
    }


    private static void editCar() {
        Dao carsDao = createDao();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Car tmpCar = new Car();

        try {
            System.out.println("Podaj identyfikator samochodu ? ");
            tmpCar.setId(Integer.parseInt(reader.readLine()));

            System.out.println("Podaj VIN ? ");
            tmpCar.setVin(Integer.parseInt(reader.readLine()));

            System.out.println("Podaj markę ?");
            tmpCar.setBrand(reader.readLine());

            System.out.println("Podaj model ?");
            tmpCar.setModel(reader.readLine());

            System.out.println("Podaj datę produkcji w formacie dd/mm/yyyy ?");
            String tmpProductionDate = reader.readLine();
            tmpCar.setProductionDateYear(Integer.parseInt(
                    tmpProductionDate.substring(6)));
            tmpCar.setProductionDateMonth(Integer.parseInt(
                    tmpProductionDate.substring(3, 5)));
            tmpCar.setProductionDateDay(Integer.parseInt(
                    tmpProductionDate.substring(0, 2)));

            System.out.println("Podaj kolor ?");
            tmpCar.setColor(reader.readLine());


        } catch (IOException e) {
            System.out.println("Wczytano błędne dane");
        }

        try {
            carsDao.updateCar(tmpCar);
            System.out.println(String.format(
                    "Samochód o id=%d został zaktualizowany w bazie danych",
                    tmpCar.getId()));

        } catch (SQLException e) {
            System.out.println("Dodanie pojazdów się nie powiodło, błąd przekazanych danych.");
            e.printStackTrace();
        }

        closeDao(carsDao);
    }


    private static void deleteCar() {
        Dao carsDao = createDao();

        int id;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Podaj identyfikator samochodu, który chcesz usunąć?");
        try {
            id = Integer.parseInt(reader.readLine());
            carsDao.deleteCar(id);

            System.out.println(String.format("Samochód o id=%d został usunięty z bazy", id));

        } catch (IOException | NumberFormatException e) {
            System.out.println("Podano nieprawidłowe dane");
        } catch (SQLException e) {
            System.out.println("Podano błędny numer id");
            e.printStackTrace();
        }

        closeDao(carsDao);
    }


    private static Dao createDao() {
        Dao carsDao = new CarsDao();

        try {
            carsDao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carsDao;
    }


    private static void closeDao(Dao dao) {
        try {
            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void showMenu() {
        System.out.println(
                "1. Dodaj samochód\n" +
                        "2. Edytuj samochód\n" +
                        "3. Usuń samochód\n" +
                        "4. Pokaż samochody\n" +
                        "5. Wyjdź");
    }
}

