package com.Server;

import com.CommandsManager;
import com.Data.Coordinates;
import com.Data.ExecuteRequest;
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
    private String owner;
    private Connection connection;
    private static HashMap<String, Boolean> userExist = new HashMap<String, Boolean>();
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private static final String GET_ALL_FROM_TABLE = "SELECT * FROM COLLECTION";
    private static final String ADD_ROUTE_REQUEST = "INSERT INTO COLLECTION (id, name_, x_coord, y_coord, z_coord, from_, to_, distance, owner_) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String REMOVE_ROUTE_BY_ID = "DELETE FROM COLLECTION WHERE id = ?";
    private static final String CHECK_ID_EXISTENCE = "SELECT COUNT(*) FROM COLLECTION WHERE id = ?";
    private static final String IS_OWNER_REQUEST = "SELECT COUNT(*) FROM COLLECTION WHERE id = ? AND owner_ = ?";
    private static final String VALIDATE_USER_REQUEST = "SELECT COUNT(*) FROM USERS WHERE username = ? AND password = ?";
    private static final String GET_MAX_ID = "SELECT MAX(id) AS id FROM COLLECTION";
    private static final String UPDATE_ROUTE_BY_ID = "";
    private static final String UPDATE_NAME_BY_ID = "UPDATE COLLECTION SET name_ = ? WHERE id = ?";
    private static final String UPDATE_X_BY_ID = "UPDATE COLLECTION SET x_coord = ? WHERE id = ?";
    private static final String UPDATE_Y_BY_ID = "UPDATE COLLECTION SET y_coord = ? WHERE id = ?";
    private static final String UPDATE_Z_BY_ID = "UPDATE COLLECTION SET z_coord = ? WHERE id = ?";
    private static final String UPDATE_FROM_BY_ID = "UPDATE COLLECTION SET from_ = ? WHERE id = ?";
    private static final String UPDATE_TO_BY_ID = "UPDATE COLLECTION SET to_ = ? WHERE id = ?";
    private static final String UPDATE_DISTANCE_BY_ID = "UPDATE COLLECTION SET distance = ? WHERE id = ?";
    private static final String REMOVE_ALL = "DELETE FROM COLLECTION WHERE owner_ = ?";
    private static final String REMOVE_LOWER = "DELETE FROM COLLECTION WHERE distance < ?";
    private static final String CHECK_USER_REQUEST = "SELECT COUNT(*) FROM USERS WHERE username = ? AND password = ?";
    private static final String CHECK_USER_EXIST = "SELECT COUNT(*) FROM USERS WHERE username = ?";


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
            synchronized (this){
                connection = DriverManager.getConnection(URL, username, password);
            }
            System.out.println("Подключение к базе данных успешно установлено!");
        } catch (SQLException throwables) {
            System.err.println("Не удалось установить соединение с базой данных. Завершение работы.");
            System.exit(-1);
        }
    }

    public Boolean checkUser(String username, String password) throws SQLException {
        PreparedStatement checkUser = connection.prepareStatement(CHECK_USER_REQUEST);
        checkUser.setString(1, username);
        checkUser.setString(2, password);
        ResultSet result = checkUser.executeQuery();
        result.next();
        if (result.getInt(1) >= 1){
            return true;
        }
        return false;
    }

    public Boolean checkUserExist(String username) throws SQLException {
        PreparedStatement checkUserExist = connection.prepareStatement(CHECK_USER_EXIST);
        checkUserExist.setString(1, username);
        ResultSet result = checkUserExist.executeQuery();
        result.next();
        if (result.getInt(1) >= 1){
            return true;
        }
        return false;
    }

    public Boolean registerUser(String username, String password) {
        try {
            password = DataHasher.get_SHA512_password(password + pepper);
            System.out.println("XXX " + username);
            this.owner = username;
            if (checkUserExist(username)) {
                System.out.println("KKK");
                if (checkUser(username, password)) {
                    System.out.println("YYY " + owner);
                    ExecuteRequest.answer.append("Авторизация произошла успешно!");
                    return true;
                }
                ExecuteRequest.answer.append("Введены некорректные данные. Повторите ввод.");
                return false;
            }
            System.out.println("ZZZ " + owner);
            PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST);
            userExist.put(username, true);
            addStatement.setString(1, username);
            addStatement.setString(2, DataHasher.get_SHA512_password(password + pepper));
            addStatement.executeUpdate();
            addStatement.close();
            System.out.println("Вы успешно авторизовались!");
            ExecuteRequest.answer.append("Авторизация произошла успешно!");
        } catch(SQLException e){
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        }
        return true;
    }


    public Boolean extractCollectionFromDB(){
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
            System.out.println("Данные успешно загружены в коллекцию!");
        } catch (SQLException throwables) {
            System.err.println("Произошла ошибка при загрузке данных из базы данных. Завершение работы.");
            System.exit(-1);
        }
        return true;
    }


    public Route extractRoute(ResultSet result) throws SQLException {
        Integer id = result.getInt("id");
        String name = result.getString("name_");
        Long x_coord = result.getLong("x_coord");
        Double y_coord = result.getDouble("y_coord");
        Double z_coord = result.getDouble("z_coord");
        Location from = Location.valueOf(result.getString("from_").toUpperCase());
        Location to = Location.valueOf(result.getString("to_").toUpperCase());
        Double dist = result.getDouble("distance");
//        System.out.println(id);
//        System.out.println(name);
//        System.out.println(x_coord);
//        System.out.println(y_coord);
//        System.out.println(z_coord);
//        System.out.println(from);
//        System.out.println(to);
//        System.out.println(dist);
        Coordinates coordinates = new Coordinates(x_coord, y_coord, z_coord);
//        System.out.println(coordinates.toString());
        Route route = new Route(id, name, coordinates, from, to, dist);
//        System.out.println(route.toString());
        return route;
    }


    public Boolean addRouteIntoDB(Route route){
        Integer id = 0;
        try {
            PreparedStatement getMaxID = connection.prepareStatement(GET_MAX_ID);
            ResultSet result = getMaxID.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            id++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String name = route.getName();
        Coordinates coordinates = route.getCoordinates();
        Location from = route.getFrom();
        Location to = route.getTo();
        Double dist = route.getDistance();
        Long x_coord = coordinates.x;
        Double y_coord = coordinates.y;
        Double z_coord = coordinates.z;
        System.out.println(this.owner);
        try {
            PreparedStatement addRouteRequest = connection.prepareStatement(ADD_ROUTE_REQUEST);
            addRouteRequest.setInt(1, id);
            addRouteRequest.setString(2, name);
            addRouteRequest.setLong(3, x_coord);
            addRouteRequest.setDouble(4, y_coord);
            addRouteRequest.setDouble(5, z_coord);
            addRouteRequest.setString(6, from.getName());
            addRouteRequest.setString(7, to.getName());
            addRouteRequest.setDouble(8, dist);
            addRouteRequest.setString(9, this.owner);
            addRouteRequest.executeUpdate();
            addRouteRequest.close();
        } catch (SQLException throwables) {
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        }
        return true;
    }


    public Boolean removeRouteByID(Integer id, String initiator) {
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
            return false;
        } catch(NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        } catch(SQLException e){
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        }
        return true;
    }

    public Boolean checkID(Integer id) throws SQLException {
        PreparedStatement checkIDRequest = connection.prepareStatement(CHECK_ID_EXISTENCE);
        checkIDRequest.setInt(1, id);
        ResultSet result = checkIDRequest.executeQuery();
        result.next();
        return result.getInt(1) >= 1;
    }

    public Boolean isOwnerOf(Integer id, String owner) throws SQLException {
        PreparedStatement isOwnerRequest = connection.prepareStatement(IS_OWNER_REQUEST);
        isOwnerRequest.setInt(1, id);
        isOwnerRequest.setString(2, owner);
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
        return true;
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
            updateNameByIDRequest.close();
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
            PreparedStatement updateXByIDRequest = connection.prepareStatement(UPDATE_X_BY_ID);
            updateXByIDRequest.setLong(1, x);
            updateXByIDRequest.setInt(2, id);
            updateXByIDRequest.executeUpdate();
            updateXByIDRequest.close();
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
            PreparedStatement updateYByIDRequest = connection.prepareStatement(UPDATE_Y_BY_ID);
            updateYByIDRequest.setDouble(1, y);
            updateYByIDRequest.setInt(2, id);
            updateYByIDRequest.executeUpdate();
            updateYByIDRequest.close();
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
            PreparedStatement updateZByIDRequest = connection.prepareStatement(UPDATE_Z_BY_ID);
            updateZByIDRequest.setDouble(1, z);
            updateZByIDRequest.setInt(2, id);
            updateZByIDRequest.executeUpdate();
            updateZByIDRequest.close();
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
            PreparedStatement updateFromByIDRequest = connection.prepareStatement(UPDATE_FROM_BY_ID);
            updateFromByIDRequest.setString(1, from);
            updateFromByIDRequest.setInt(2, id);
            updateFromByIDRequest.executeUpdate();
            updateFromByIDRequest.close();
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
            PreparedStatement updateToByIDRequest = connection.prepareStatement(UPDATE_TO_BY_ID);
            updateToByIDRequest.setString(1, to);
            updateToByIDRequest.setInt(2, id);
            updateToByIDRequest.executeUpdate();
            updateToByIDRequest.close();
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
            PreparedStatement updateDistanceByIDRequest = connection.prepareStatement(UPDATE_DISTANCE_BY_ID);
            updateDistanceByIDRequest.setDouble(1, distance);
            updateDistanceByIDRequest.setInt(2, id);
            updateDistanceByIDRequest.executeUpdate();
            updateDistanceByIDRequest.close();
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }

    public Boolean removeAll(String initiator){
        try {
            PreparedStatement removeAllRequest = connection.prepareStatement(REMOVE_ALL);
            removeAllRequest.setString(1, initiator);
            removeAllRequest.executeUpdate();
            removeAllRequest.close();
        } catch (SQLException e) {
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        }
        return true;
    }

    public Boolean removeLower(Route route, String initiator){
        try {
            if (!isOwnerOf(route.getId(), initiator)){
                throw new NoRightsToTheFileException();
            }
            PreparedStatement removeLower = connection.prepareStatement(REMOVE_LOWER);
            removeLower.setDouble(1, route.getDistance());
            removeLower.executeUpdate();
            removeLower.close();
        } catch (SQLException e) {
            System.err.println("Неверный запрос. Завершаю работу.");
            System.exit(-1);
        } catch (NoRightsToTheFileException e){
            System.err.println("У вас нету доступа к запрашиваемым данным.");
            return false;
        }
        return true;
    }
}
