package com.Server;

import com.CommandsManager;
import com.Data.Coordinates;
import com.Data.Location;
import com.Data.Route;
import com.DataBaseUtility.DataHasher;
import com.Exceptions.NoRightsToTheFileException;
import com.Exceptions.NoSuchIDException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static final String pepper = "aEB@G#LT&*wH";
    private String URL;
    private String username;
    private String password;
    private Connection connection;
    private static HashMap<String, Boolean> userExist = new HashMap<String, Boolean>();
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private static final String GET_ALL_FROM_TABLE = "SELECT * FROM ROUTE";
    private static final String ADD_ROUTE_REQUEST = "INSERT INTO DATA (id, name, x, y, z, from, to, distance, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String REMOVE_ROUTE_BY_ID = "DELETE FROM DATA WHERE id = ?";
    private static final String CHECK_ID_EXISTENCE = "SELECT COUNT(*) FROM DATA WHERE id = ?";
    private static final String IS_OWNER_REQUEST = "SELECT COUNT(*) FROM DATA WHERE id = ? AND owner = ?";
    private static final String VALIDATE_USER_REQUEST = "SELECT COUNT(*) FROM USERS WHERE username = ? AND password = ?";
    private static final String UPDATE_ROUTE_BY_ID = "";
    private static final String UPDATE_NAME_BY_ID = "UPDATE DATA SET name = ? WHERE id = ?";
    private static final String UPDATE_X_BY_ID = "UPDATE DATA SET x = ? WHERE id = ?";
    private static final String UPDATE_Y_BY_ID = "UPDATE DATA SET y = ? WHERE id = ?";
    private static final String UPDATE_Z_BY_ID = "UPDATE DATA SET z = ? WHERE id = ?";
    private static final String UPDATE_FROM_BY_ID = "UPDATE DATA SET from = ? WHERE id = ?";
    private static final String UPDATE_TO_BY_ID = "UPDATE DATA SET to = ? WHERE id = ?";
    private static final String UPDATE_DISTANCE_BY_ID = "UPDATE DATA SET distance = ? WHERE id = ?";

    private CommandsManager commandsManager = new CommandsManager();



    public DataBase(String URL, String username, String password){
        this.URL = URL;
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }


    public void connectToDatabase(){
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Подключение к базе данных успешно установлено!");
        } catch (SQLException throwables) {
            System.err.println("Не удалось установить соединение с базой данных. Завершение работы.");
            System.exit(-1);
        }
    }


    public boolean registerUser(String username, String password) throws SQLException {
        if (userExist.get(username)){
            return false;
        }
        PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST);
        userExist.put(username, true);
        addStatement.setString(1, username);
        addStatement.setString(2, DataHasher.get_SHA512_password(password + pepper));
        addStatement.executeUpdate();
        addStatement.close();
        return true;
    }


    public void extractCollectionFromDB(){
        try{
            PreparedStatement getStatement = connection.prepareStatement(GET_ALL_FROM_TABLE);
            ResultSet resultSet = getStatement.executeQuery();
            while(resultSet.next()){
                try{
                    Route route = extractRoute(resultSet);
                    commandsManager.add(route);
                } catch (IOException e){
                    System.err.println("Некорректные данные.");
                    System.exit(-1);
                }
            }
            getStatement.close();
            System.out.println("Данные успешно загружены в коллекцию!");
        } catch (SQLException throwables) {
            System.err.println("Произошла ошибка при загрузке данных из базы данных. Завершение работы.");
            System.exit(-1);
        }
    }


    public Route extractRoute(ResultSet result) throws SQLException {
        Integer id = result.getInt("id");
        String name = result.getString("name");
        Long x_coord = result.getLong("x");
        Double y_coord = result.getDouble("y");
        Double z_coord = result.getDouble("z");
        Location from = Location.valueOf(result.getString("from"));
        Location to = Location.valueOf(result.getString("to"));
        Double dist = result.getDouble("distance");
        Coordinates coordinates = new Coordinates(x_coord, y_coord, z_coord);
        Route route = new Route(id, name, coordinates, from, to, dist);
        return route;
    }


    public void addRouteIntoDB(Route route, String owner){
        Integer id = route.getId();
        String name = route.getName();
        Coordinates coordinates = route.getCoordinates();
        Location from = route.getFrom();
        Location to = route.getTo();
        Double dist = route.getDistance();
        Long x_coord = coordinates.x;
        Double y_coord = coordinates.y;
        Double z_coord = coordinates.z;
        try {
            PreparedStatement addRouteRequest = connection.prepareStatement(ADD_ROUTE_REQUEST);
            addRouteRequest.setInt(1, id);
            addRouteRequest.setString(2, name);
            addRouteRequest.setLong(3, x_coord);
            addRouteRequest.setDouble(4, y_coord);
            addRouteRequest.setDouble(5, z_coord);
            addRouteRequest.setString(6, from.getName());
            addRouteRequest.setString(7, to.getName());
            addRouteRequest.setString(8, owner);
            addRouteRequest.executeUpdate();
            addRouteRequest.close();
        } catch (SQLException throwables) {
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        }
    }


    public void removeRouteByID(Integer id, String initiator) throws SQLException {
        try {
            if (!checkID(id)) {
                throw new NoSuchIDException();
            }
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement removeRouteByIDRequest = connection.prepareStatement(REMOVE_ROUTE_BY_ID);
            connection.setAutoCommit(false);
            connection.setSavepoint();
            removeRouteByIDRequest.setInt(1, id);
            removeRouteByIDRequest.executeUpdate();
            removeRouteByIDRequest.close();
            connection.commit();
            connection.setAutoCommit(true);
        } catch(NoSuchIDException e){
            System.err.println("Поле с данным id не найдено.");
        } catch(NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
        }
    }

    public Boolean checkID(Integer id) throws SQLException {
        PreparedStatement checkIDRequest = connection.prepareStatement(CHECK_ID_EXISTENCE);
        checkIDRequest.setInt(1, id);
        ResultSet result = checkIDRequest.executeQuery();
        result.next();
        return result.getInt(1) >= 1;
    }

    public Boolean isOwnerOf(Integer id, String initiator) throws SQLException {
        PreparedStatement isOwnerRequest = connection.prepareStatement(IS_OWNER_REQUEST);
        isOwnerRequest.setInt(1, id);
        isOwnerRequest.setString(2, initiator);
        ResultSet result = isOwnerRequest.executeQuery();
        result.next();
        return result.getInt(1) >= 1;
    }

    public Boolean validateUser(String username, String password) throws SQLException {
        PreparedStatement validateUserRequest = connection.prepareStatement(VALIDATE_USER_REQUEST);
        validateUserRequest.setString(1, username);
        validateUserRequest.setString(2, password);
        ResultSet result = validateUserRequest.executeQuery();
        result.next();
        return result.getInt(1) >= 1;
    }

    public Boolean updateRouteByID(Integer id, Route route, String initiator){
        try {
            if (!checkID(id)){
                throw new NoSuchIDException();
            }
            String name = route.getName();
            Coordinates coordinates = route.getCoordinates();
            Long x_coord = coordinates.x;
            Double y_coord = coordinates.y;
            Double z_coord = coordinates.z;
            String from = route.getFrom().getName();
            String to = route.getTo().getName();
            Double distance = route.getDistance();
            if (updateNameByID(id, name, initiator) && updateXByID(id, x_coord, initiator) && updateYByID(id, y_coord, initiator) &&
                    updateZByID(id, z_coord, initiator) && updateFromByID(id, from, initiator) &&
                    updateToByID(id, to, initiator) && updateDistanceByID(id, distance, initiator)){
                System.out.println("Данные успешно обновлены!");
            }

        } catch (SQLException e){
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        } catch (NoSuchIDException e){
            System.err.println("Поле с данным id не найдено.");
            return false;
        }
    }

    public Boolean updateNameByID(Integer id, String name, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setString(1, name);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean updateXByID(Integer id, Long x, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setLong(1, x);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean updateYByID(Integer id, Double y, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setDouble(1, y);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean updateZByID(Integer id, Double z, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setDouble(1, z);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean updateFromByID(Integer id, String from, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setString(1, from);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean updateToByID(Integer id, String to, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setString(1, to);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean updateDistanceByID(Integer id, Double distance, String initiator) throws SQLException {
        try {
            if (!isOwnerOf(id, initiator)) {
                throw new NoRightsToTheFileException();
            }
            PreparedStatement updateNameByIDRequest = connection.prepareStatement(UPDATE_NAME_BY_ID);
            updateNameByIDRequest.setDouble(1, distance);
            updateNameByIDRequest.setInt(2, id);
            updateNameByIDRequest.executeUpdate();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }
}
