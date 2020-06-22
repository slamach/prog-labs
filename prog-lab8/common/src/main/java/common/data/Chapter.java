package common.data;

import java.io.Serializable;

/**
 * Chapter with marines.
 */
public class Chapter implements Serializable {
    private String name;
    private long marinesCount;

    public Chapter(String name, long marinesCount) {
        this.name = name;
        this.marinesCount = marinesCount;
    }

    /**
     * @return Name of the chapter.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Number of marines in the chapter.
     */
    public long getMarinesCount() {
        return marinesCount;
    }

    @Override
    public String toString() {
        return name + " (~" + marinesCount + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + (int) marinesCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Chapter) {
            Chapter chapterObj = (Chapter) obj;
            return name.equals(chapterObj.getName()) && (marinesCount == chapterObj.getMarinesCount());
        }
        return false;
    }
}
