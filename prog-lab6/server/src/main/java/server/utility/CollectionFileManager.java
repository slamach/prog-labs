package server.utility;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import common.data.SpaceMarine;
import common.utility.Outputer;
import server.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Operates the file for saving/loading collection.
 */
public class CollectionFileManager {
    private Gson gson = new Gson();
    private String envVariable;

    public CollectionFileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    /**
     * Writes collection to a file.
     *
     * @param collection Collection to write.
     */
    public void writeCollection(Collection<?> collection) {
        if (System.getenv().get(envVariable) != null) {
            try (FileWriter collectionFileWriter = new FileWriter(new File(System.getenv().get(envVariable)))) {
                collectionFileWriter.write(gson.toJson(collection));
                ResponseOutputer.appendln("Коллекция успешно сохранена в файл.");
            } catch (IOException exception) {
                ResponseOutputer.appenderror("Загрузочный файл является директорией/не может быть открыт!");
            }
        } else ResponseOutputer.appenderror("Системная переменная с загрузочным файлом не найдена!");
    }

    /**
     * Reads collection from a file.
     *
     * @return Readed collection.
     */
    public TreeSet<SpaceMarine> readCollection() {
        if (System.getenv().get(envVariable) != null) {
            try (Scanner collectionFileScanner = new Scanner(new File(System.getenv().get(envVariable)))) {
                TreeSet<SpaceMarine> collection;
                Type collectionType = new TypeToken<TreeSet<SpaceMarine>>() {
                }.getType();
                collection = gson.fromJson(collectionFileScanner.nextLine().trim(), collectionType);
                Outputer.println("Коллекция успешно загружена.");
                App.logger.info("Коллекция успешно загружена.");
                return collection;
            } catch (FileNotFoundException exception) {
                Outputer.printerror("Загрузочный файл не найден!");
                App.logger.warn("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Загрузочный файл пуст!");
                App.logger.error("Загрузочный файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                Outputer.printerror("В загрузочном файле не обнаружена корректная коллекция!");
                App.logger.error("В загрузочном файле не обнаружена корректная коллекция!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                App.logger.fatal("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else Outputer.printerror("Системная переменная с загрузочным файлом не найдена!");
        return new TreeSet<>();
    }
}
