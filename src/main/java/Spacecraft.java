import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Spacecraft {
    Integer id;
    String name;
    Integer service_life;
    Integer birth_year;

    public Spacecraft(Integer id, String name, Integer service_life, Integer birth_year) {
        this.id = id;
        this.name = name;
        this.service_life = service_life;
        this.birth_year = birth_year;
    }

    public List<Spacecraft> SpacecraftArray(Integer count){
        List<Spacecraft> array = new ArrayList<>();
        Random random;
        RandomStringGenerator

        for(int i = 0;i < count;i++){

        }


        return array;
    }


}
