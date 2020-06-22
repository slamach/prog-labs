package server.utility;

import common.data.*;
import common.exceptions.DatabaseHandlingException;
import common.interaction.MarineRaw;
import common.interaction.User;
import server.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Operates the database collection itself.
 */
public class DatabaseCollectionManager {
    // MARINE_TABLE
    private final String SELECT_ALL_MARINES = "SELECT * FROM " + DatabaseHandler.MARINE_TABLE;
    private final String SELECT_MARINE_BY_ID = SELECT_ALL_MARINES + " WHERE " +
            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_MARINE_BY_ID_AND_USER_ID = SELECT_MARINE_BY_ID + " AND " +
            DatabaseHandler.MARINE_TABLE_USER_ID_COLUMN + " = ?";
    private final String INSERT_MARINE = "INSERT INTO " +
            DatabaseHandler.MARINE_TABLE + " (" +
            DatabaseHandler.MARINE_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_HEALTH_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_CATEGORY_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_WEAPON_TYPE_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_MELEE_WEAPON_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_CHAPTER_ID_COLUMN + ", " +
            DatabaseHandler.MARINE_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?::astartes_category," +
            "?::weapon, ?::melee_weapon, ?, ?)";
    private final String DELETE_MARINE_BY_ID = "DELETE FROM " + DatabaseHandler.MARINE_TABLE +
            " WHERE " + DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MARINE_NAME_BY_ID = "UPDATE " + DatabaseHandler.MARINE_TABLE + " SET " +
            DatabaseHandler.MARINE_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MARINE_HEALTH_BY_ID = "UPDATE " + DatabaseHandler.MARINE_TABLE + " SET " +
            DatabaseHandler.MARINE_TABLE_HEALTH_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MARINE_CATEGORY_BY_ID = "UPDATE " + DatabaseHandler.MARINE_TABLE + " SET " +
            DatabaseHandler.MARINE_TABLE_CATEGORY_COLUMN + " = ?::astartes_category" + " WHERE " +
            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MARINE_WEAPON_TYPE_BY_ID = "UPDATE " + DatabaseHandler.MARINE_TABLE + " SET " +
            DatabaseHandler.MARINE_TABLE_WEAPON_TYPE_COLUMN + " = ?::weapon" + " WHERE " +
            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MARINE_MELEE_WEAPON_BY_ID = "UPDATE " + DatabaseHandler.MARINE_TABLE + " SET " +
            DatabaseHandler.MARINE_TABLE_MELEE_WEAPON_COLUMN + " = ?::melee_weapon" + " WHERE " +
            DatabaseHandler.MARINE_TABLE_ID_COLUMN + " = ?";
    // COORDINATES_TABLE
    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseHandler.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_MARINE_ID = SELECT_ALL_COORDINATES +
            " WHERE " + DatabaseHandler.COORDINATES_TABLE_SPACE_MARINE_ID_COLUMN + " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
            DatabaseHandler.COORDINATES_TABLE + " (" +
            DatabaseHandler.COORDINATES_TABLE_SPACE_MARINE_ID_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?, ?)";
    private final String UPDATE_COORDINATES_BY_MARINE_ID = "UPDATE " + DatabaseHandler.COORDINATES_TABLE + " SET " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.COORDINATES_TABLE_SPACE_MARINE_ID_COLUMN + " = ?";
    // CHAPTER_TABLE
    private final String SELECT_ALL_CHAPTER = "SELECT * FROM " + DatabaseHandler.CHAPTER_TABLE;
    private final String SELECT_CHAPTER_BY_ID = SELECT_ALL_CHAPTER +
            " WHERE " + DatabaseHandler.CHAPTER_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_CHAPTER = "INSERT INTO " +
            DatabaseHandler.CHAPTER_TABLE + " (" +
            DatabaseHandler.CHAPTER_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.CHAPTER_TABLE_MARINES_COUNT_COLUMN + ") VALUES (?, ?)";
    private final String UPDATE_CHAPTER_BY_ID = "UPDATE " + DatabaseHandler.CHAPTER_TABLE + " SET " +
            DatabaseHandler.CHAPTER_TABLE_NAME_COLUMN + " = ?, " +
            DatabaseHandler.CHAPTER_TABLE_MARINES_COUNT_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.CHAPTER_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_CHAPTER_BY_ID = "DELETE FROM " + DatabaseHandler.CHAPTER_TABLE +
            " WHERE " + DatabaseHandler.CHAPTER_TABLE_ID_COLUMN + " = ?";
    private DatabaseHandler databaseHandler;
    private DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseHandler databaseHandler, DatabaseUserManager databaseUserManager) {
        this.databaseHandler = databaseHandler;
        this.databaseUserManager = databaseUserManager;
    }

    /**
     * Create Marine.
     *
     * @param resultSet Result set parametres of Marine.
     * @return New Marine.
     * @throws SQLException When there's exception inside.
     */
    private SpaceMarine createMarine(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DatabaseHandler.MARINE_TABLE_ID_COLUMN);
        String name = resultSet.getString(DatabaseHandler.MARINE_TABLE_NAME_COLUMN);
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseHandler.MARINE_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        double health = resultSet.getDouble(DatabaseHandler.MARINE_TABLE_HEALTH_COLUMN);
        AstartesCategory category = AstartesCategory.valueOf(resultSet.getString(DatabaseHandler.MARINE_TABLE_CATEGORY_COLUMN));
        Weapon weaponType = Weapon.valueOf(resultSet.getString(DatabaseHandler.MARINE_TABLE_WEAPON_TYPE_COLUMN));
        MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(resultSet.getString(DatabaseHandler.MARINE_TABLE_MELEE_WEAPON_COLUMN));
        Coordinates coordinates = getCoordinatesByMarineId(id);
        Chapter chapter = getChapterById(resultSet.getLong(DatabaseHandler.MARINE_TABLE_CHAPTER_ID_COLUMN));
        User owner = databaseUserManager.getUserById(resultSet.getLong(DatabaseHandler.MARINE_TABLE_USER_ID_COLUMN));
        return new SpaceMarine(
                id,
                name,
                coordinates,
                creationDate,
                health,
                category,
                weaponType,
                meleeWeapon,
                chapter,
                owner
        );
    }

    /**
     * @return List of Marines.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public NavigableSet<SpaceMarine> getCollection() throws DatabaseHandlingException {
        NavigableSet<SpaceMarine> marineList = new TreeSet<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = databaseHandler.getPreparedStatement(SELECT_ALL_MARINES, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                marineList.add(createMarine(resultSet));
            }
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectAllStatement);
        }
        return marineList;
    }

    /**
     * @param marineId Id of Marine.
     * @return Chapter id.
     * @throws SQLException When there's exception inside.
     */
    private long getChapterIdByMarineId(long marineId) throws SQLException {
        long chapterId;
        PreparedStatement preparedSelectMarineByIdStatement = null;
        try {
            preparedSelectMarineByIdStatement = databaseHandler.getPreparedStatement(SELECT_MARINE_BY_ID, false);
            preparedSelectMarineByIdStatement.setLong(1, marineId);
            ResultSet resultSet = preparedSelectMarineByIdStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_MARINE_BY_ID.");
            if (resultSet.next()) {
                chapterId = resultSet.getLong(DatabaseHandler.MARINE_TABLE_CHAPTER_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_MARINE_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectMarineByIdStatement);
        }
        return chapterId;
    }

    /**
     * @param marineId Id of Marine.
     * @return coordinates.
     * @throws SQLException When there's exception inside.
     */
    private Coordinates getCoordinatesByMarineId(long marineId) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedSelectCoordinatesByMarineIdStatement = null;
        try {
            preparedSelectCoordinatesByMarineIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_COORDINATES_BY_MARINE_ID, false);
            preparedSelectCoordinatesByMarineIdStatement.setLong(1, marineId);
            ResultSet resultSet = preparedSelectCoordinatesByMarineIdStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_COORDINATES_BY_MARINE_ID.");
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getDouble(DatabaseHandler.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseHandler.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_MARINE_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectCoordinatesByMarineIdStatement);
        }
        return coordinates;
    }

    /**
     * @param chapterId Id of Chapter.
     * @return Chapter.
     * @throws SQLException When there's exception inside.
     */
    private Chapter getChapterById(long chapterId) throws SQLException {
        Chapter chapter;
        PreparedStatement preparedSelectChapterByIdStatement = null;
        try {
            preparedSelectChapterByIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_CHAPTER_BY_ID, false);
            preparedSelectChapterByIdStatement.setLong(1, chapterId);
            ResultSet resultSet = preparedSelectChapterByIdStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_CHAPTER_BY_ID.");
            if (resultSet.next()) {
                chapter = new Chapter(
                        resultSet.getString(DatabaseHandler.CHAPTER_TABLE_NAME_COLUMN),
                        resultSet.getLong(DatabaseHandler.CHAPTER_TABLE_MARINES_COUNT_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_CHAPTER_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectChapterByIdStatement);
        }
        return chapter;
    }

    /**
     * @param marineRaw Marine raw.
     * @param user      User.
     * @return Marine.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public SpaceMarine insertMarine(MarineRaw marineRaw, User user) throws DatabaseHandlingException {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        SpaceMarine marine;
        PreparedStatement preparedInsertMarineStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertChapterStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            LocalDateTime creationTime = LocalDateTime.now();

            preparedInsertMarineStatement = databaseHandler.getPreparedStatement(INSERT_MARINE, true);
            preparedInsertCoordinatesStatement = databaseHandler.getPreparedStatement(INSERT_COORDINATES, true);
            preparedInsertChapterStatement = databaseHandler.getPreparedStatement(INSERT_CHAPTER, true);

            preparedInsertChapterStatement.setString(1, marineRaw.getChapter().getName());
            preparedInsertChapterStatement.setLong(2, marineRaw.getChapter().getMarinesCount());
            if (preparedInsertChapterStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedChapterKeys = preparedInsertChapterStatement.getGeneratedKeys();
            long chapterId;
            if (generatedChapterKeys.next()) {
                chapterId = generatedChapterKeys.getLong(1);
            } else throw new SQLException();
            App.logger.info("Выполнен запрос INSERT_CHAPTER.");

            preparedInsertMarineStatement.setString(1, marineRaw.getName());
            preparedInsertMarineStatement.setTimestamp(2, Timestamp.valueOf(creationTime));
            preparedInsertMarineStatement.setDouble(3, marineRaw.getHealth());
            preparedInsertMarineStatement.setString(4, marineRaw.getCategory().toString());
            preparedInsertMarineStatement.setString(5, marineRaw.getWeaponType().toString());
            preparedInsertMarineStatement.setString(6, marineRaw.getMeleeWeapon().toString());
            preparedInsertMarineStatement.setLong(7, chapterId);
            preparedInsertMarineStatement.setLong(8, databaseUserManager.getUserIdByUsername(user));
            if (preparedInsertMarineStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedMarineKeys = preparedInsertMarineStatement.getGeneratedKeys();
            long spaceMarineId;
            if (generatedMarineKeys.next()) {
                spaceMarineId = generatedMarineKeys.getLong(1);
            } else throw new SQLException();
            App.logger.info("Выполнен запрос INSERT_MARINE.");

            preparedInsertCoordinatesStatement.setLong(1, spaceMarineId);
            preparedInsertCoordinatesStatement.setDouble(2, marineRaw.getCoordinates().getX());
            preparedInsertCoordinatesStatement.setFloat(3, marineRaw.getCoordinates().getY());
            if (preparedInsertCoordinatesStatement.executeUpdate() == 0) throw new SQLException();
            App.logger.info("Выполнен запрос INSERT_COORDINATES.");

            marine = new SpaceMarine(
                    spaceMarineId,
                    marineRaw.getName(),
                    marineRaw.getCoordinates(),
                    creationTime,
                    marineRaw.getHealth(),
                    marineRaw.getCategory(),
                    marineRaw.getWeaponType(),
                    marineRaw.getMeleeWeapon(),
                    marineRaw.getChapter(),
                    user
            );

            databaseHandler.commit();
            return marine;
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении группы запросов на добавление нового объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertMarineStatement);
            databaseHandler.closePreparedStatement(preparedInsertCoordinatesStatement);
            databaseHandler.closePreparedStatement(preparedInsertChapterStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * @param marineRaw Marine raw.
     * @param marineId  Id of Marine.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void updateMarineById(long marineId, MarineRaw marineRaw) throws DatabaseHandlingException {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        PreparedStatement preparedUpdateMarineNameByIdStatement = null;
        PreparedStatement preparedUpdateMarineHealthByIdStatement = null;
        PreparedStatement preparedUpdateMarineCategoryByIdStatement = null;
        PreparedStatement preparedUpdateMarineWeaponTypeByIdStatement = null;
        PreparedStatement preparedUpdateMarineMeleeWeaponByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByMarineIdStatement = null;
        PreparedStatement preparedUpdateChapterByIdStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            preparedUpdateMarineNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MARINE_NAME_BY_ID, false);
            preparedUpdateMarineHealthByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MARINE_HEALTH_BY_ID, false);
            preparedUpdateMarineCategoryByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MARINE_CATEGORY_BY_ID, false);
            preparedUpdateMarineWeaponTypeByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MARINE_WEAPON_TYPE_BY_ID, false);
            preparedUpdateMarineMeleeWeaponByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MARINE_MELEE_WEAPON_BY_ID, false);
            preparedUpdateCoordinatesByMarineIdStatement = databaseHandler.getPreparedStatement(UPDATE_COORDINATES_BY_MARINE_ID, false);
            preparedUpdateChapterByIdStatement = databaseHandler.getPreparedStatement(UPDATE_CHAPTER_BY_ID, false);

            if (marineRaw.getName() != null) {
                preparedUpdateMarineNameByIdStatement.setString(1, marineRaw.getName());
                preparedUpdateMarineNameByIdStatement.setLong(2, marineId);
                if (preparedUpdateMarineNameByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_MARINE_NAME_BY_ID.");
            }
            if (marineRaw.getCoordinates() != null) {
                preparedUpdateCoordinatesByMarineIdStatement.setDouble(1, marineRaw.getCoordinates().getX());
                preparedUpdateCoordinatesByMarineIdStatement.setFloat(2, marineRaw.getCoordinates().getY());
                preparedUpdateCoordinatesByMarineIdStatement.setLong(3, marineId);
                if (preparedUpdateCoordinatesByMarineIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_COORDINATES_BY_MARINE_ID.");
            }
            if (marineRaw.getHealth() != -1) {
                preparedUpdateMarineHealthByIdStatement.setDouble(1, marineRaw.getHealth());
                preparedUpdateMarineHealthByIdStatement.setLong(2, marineId);
                if (preparedUpdateMarineHealthByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_MARINE_HEALTH_BY_ID.");
            }
            if (marineRaw.getCategory() != null) {
                preparedUpdateMarineCategoryByIdStatement.setString(1, marineRaw.getCategory().toString());
                preparedUpdateMarineCategoryByIdStatement.setLong(2, marineId);
                if (preparedUpdateMarineCategoryByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_MARINE_CATEGORY_BY_ID.");
            }
            if (marineRaw.getWeaponType() != null) {
                preparedUpdateMarineWeaponTypeByIdStatement.setString(1, marineRaw.getWeaponType().toString());
                preparedUpdateMarineWeaponTypeByIdStatement.setLong(2, marineId);
                if (preparedUpdateMarineWeaponTypeByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_MARINE_WEAPON_TYPE_BY_ID.");
            }
            if (marineRaw.getMeleeWeapon() != null) {
                preparedUpdateMarineMeleeWeaponByIdStatement.setString(1, marineRaw.getMeleeWeapon().toString());
                preparedUpdateMarineMeleeWeaponByIdStatement.setLong(2, marineId);
                if (preparedUpdateMarineMeleeWeaponByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_MARINE_MELEE_WEAPON_BY_ID.");
            }
            if (marineRaw.getChapter() != null) {
                preparedUpdateChapterByIdStatement.setString(1, marineRaw.getChapter().getName());
                preparedUpdateChapterByIdStatement.setLong(2, marineRaw.getChapter().getMarinesCount());
                preparedUpdateChapterByIdStatement.setLong(3, getChapterIdByMarineId(marineId));
                if (preparedUpdateChapterByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_CHAPTER_BY_ID.");
            }

            databaseHandler.commit();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedUpdateMarineNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMarineHealthByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMarineCategoryByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMarineWeaponTypeByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMarineMeleeWeaponByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateCoordinatesByMarineIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateChapterByIdStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * Delete Marine by id.
     *
     * @param marineId Id of Marine.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void deleteMarineById(long marineId) throws DatabaseHandlingException {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        PreparedStatement preparedDeleteChapterByIdStatement = null;
        try {
            preparedDeleteChapterByIdStatement = databaseHandler.getPreparedStatement(DELETE_CHAPTER_BY_ID, false);
            preparedDeleteChapterByIdStatement.setLong(1, getChapterIdByMarineId(marineId));
            if (preparedDeleteChapterByIdStatement.executeUpdate() == 0) Outputer.println(3);
            App.logger.info("Выполнен запрос DELETE_CHAPTER_BY_ID.");
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса DELETE_CHAPTER_BY_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedDeleteChapterByIdStatement);
        }
    }

    /**
     * Checks Marine user id.
     *
     * @param marineId Id of Marine.
     * @param user     Owner of marine.
     * @return Is everything ok.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean checkMarineUserId(long marineId, User user) throws DatabaseHandlingException {
        PreparedStatement preparedSelectMarineByIdAndUserIdStatement = null;
        try {
            preparedSelectMarineByIdAndUserIdStatement = databaseHandler.getPreparedStatement(SELECT_MARINE_BY_ID_AND_USER_ID, false);
            preparedSelectMarineByIdAndUserIdStatement.setLong(1, marineId);
            preparedSelectMarineByIdAndUserIdStatement.setLong(2, databaseUserManager.getUserIdByUsername(user));
            ResultSet resultSet = preparedSelectMarineByIdAndUserIdStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_MARINE_BY_ID_AND_USER_ID.");
            return resultSet.next();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_MARINE_BY_ID_AND_USER_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectMarineByIdAndUserIdStatement);
        }
    }

    /**
     * Clear the collection.
     *
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void clearCollection() throws DatabaseHandlingException {
        NavigableSet<SpaceMarine> marineList = getCollection();
        for (SpaceMarine marine : marineList) {
            deleteMarineById(marine.getId());
        }
    }
}
