package run;

import utility.*;
import org.reflections.Reflections;
import java.util.Set;

public class AdditionalTask {
    public static void main(String args[]) {
        System.out.println(isSomewhereInEnum("SUMMER"));
        System.out.println(isSomewhereInEnum("WINTER"));
        System.out.println(isSomewhereInEnum("PISMAK"));
    }

    public static Object isSomewhereInEnum(String value) {
        for (Class<? extends Enum> clazz : findEnums("utility")) {
            if (isInEnum(value, clazz)) {
                Object obj = Enum.valueOf(clazz, value);
                return obj;
            }
        }
        return null;
    }

    public static Set<Class<? extends Enum>>  findEnums(String myPackage) {
        Reflections reflections = new Reflections(myPackage);    
        return reflections.getSubTypesOf(Enum.class);
    }

    public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
          if(e.name().equals(value)) return true;
        }
        return false;
      }
}
