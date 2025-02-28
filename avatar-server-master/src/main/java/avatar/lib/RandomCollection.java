
package avatar.lib;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import lombok.Getter;


public class RandomCollection<E> {

    @Getter
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) {
            return this;
        }
        total += weight;
        map.put(total, result);
        return this;
    }

    public boolean isEmpty() {
        return map.size() == 0;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}

