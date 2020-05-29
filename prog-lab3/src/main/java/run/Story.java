package run;

import core.*;

public class Story {
    public static void main(String[] args) {
        Shores aShores = new Shores();
        Summer aSummer = new Summer();
        Hopes aHopes = new Hopes();
        aSummer.fulfillHopes(aHopes);

        String[] impressions = {"своей драматической встречи",
                                "переживаний этой ночи и опасного берега"};
        aShores.becomeImpressed(impressions);

        Talks aTalks = new Talks();
        aTalks.notNeeded();

        Boat aBoat = new Boat();
        aBoat.semicircle();
    }
}
