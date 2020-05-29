package server.utility;

import common.exceptions.DatabaseHandlingException;
import common.interaction.User;
import server.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A manager of user database.
 */
public class DatabaseUserManager {
    // USER_TABLE
    private final String SELECT_USER_BY_ID = "SELECT * FROM " + DatabaseHandler.USER_TABLE +
            " WHERE " + DatabaseHandler.USER_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + DatabaseHandler.USER_TABLE +
            " WHERE " + DatabaseHandler.USER_TABLE_USERNAME_COLUMN + " = ?";
    private final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
            DatabaseHandler.USER_TABLE_PASSWORD_COLUMN + " = ?";
    private final String INSERT_USER = "INSERT INTO " +
            DatabaseHandler.USER_TABLE + " (" +
            DatabaseHandler.USER_TABLE_USERNAME_COLUMN + ", " +
            DatabaseHandler.USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?)";

    private DatabaseHandler databaseHandler;

    public DatabaseUserManager(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    /**
     * @param userId User id.
     * @return User by id.
     * @throws SQLException When there's exception inside.
     */
    public User getUserById(long userId) throws SQLException {
        User user;
        PreparedStatement preparedSelectUserByIdStatement = null;
        try {
            preparedSelectUserByIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_USER_BY_ID, false);
            preparedSelectUserByIdStatement.setLong(1, userId);
            ResultSet resultSet = preparedSelectUserByIdStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_USER_BY_ID.");
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(DatabaseHandler.USER_TABLE_USERNAME_COLUMN),
                        resultSet.getString(DatabaseHandler.USER_TABLE_PASSWORD_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByIdStatement);
        }
        return user;
    }

    /**
     * Check user by username and password.
     *
     * @param user User.
     * @return Result set.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean checkUserByUsernameAndPassword(User user) throws DatabaseHandlingException {
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectUserByUsernameAndPasswordStatement =
                    databaseHandler.getPreparedStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, user.getUsername());
            preparedSelectUserByUsernameAndPasswordStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
            return resultSet.next();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }

    /**
     * Get user id by username.
     *
     * @param user User.
     * @return User id.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public long getUserIdByUsername(User user) throws DatabaseHandlingException {
        long userId;
        PreparedStatement preparedSelectUserByUsernameStatement = null;
        try {
            preparedSelectUserByUsernameStatement =
                    databaseHandler.getPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedSelectUserByUsernameStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedSelectUserByUsernameStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME.");
            if (resultSet.next()) {
                userId = resultSet.getLong(DatabaseHandler.USER_TABLE_ID_COLUMN);
            } else userId = -1;
            return userId;
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByUsernameStatement);
        }
    }

    /**
     * Insert user.
     *
     * @param user User.
     * @return Status of insert.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean insertUser(User user) throws DatabaseHandlingException {
        PreparedStatement preparedInsertUserStatement = null;
        try {
            if (getUserIdByUsername(user) != -1) return false;
            preparedInsertUserStatement =
                    databaseHandler.getPreparedStatement(INSERT_USER, false);
            preparedInsertUserStatement.setString(1, user.getUsername());
            preparedInsertUserStatement.setString(2, user.getPassword());
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            App.logger.info("Выполнен запрос INSERT_USER.");
            return true;
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertUserStatement);
        }
    }
}
