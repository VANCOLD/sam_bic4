package fh.technikum.sam.etc;

import fh.technikum.sam.utils.Tuple;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TupleTest {

    @Test
    public void tupleRegularTest() {
        Tuple<String, Integer> tuple = new Tuple<>("Key1", 123);
        assertThat(tuple.getKey()).isEqualTo("Key1");
        assertThat(tuple.getValue()).isEqualTo(123);
    }

    @Test
    public void tupleSetterTest() {
        Tuple<String, Integer> tuple = new Tuple<>("Key1", 123);

        assertThat(tuple.getKey()).isEqualTo("Key1");
        assertThat(tuple.getValue()).isEqualTo(123);


        tuple.setKey("KeyX");
        tuple.setValue(321);

        assertThat(tuple.getKey()).isEqualTo("KeyX");
        assertThat(tuple.getValue()).isEqualTo(321);
    }

    @Test
    public void tupleToStringTest() {
        Tuple<String, Integer> tuple = new Tuple<>("Key1", 123);
        assertThat(tuple.toString()).isEqualTo("Tuple(key=Key1, value=123)");
    }

    @Test
    public void tupleEqualsRegularTest() {
        Tuple<String, Integer> tuple1 = new Tuple<>("Key1", 123);
        Tuple<String, Integer> tuple2 = new Tuple<>("Key1", 123);

        assertThat(tuple1.equals(tuple2)).isTrue();
    }

    @Test
    public void tupleEqualsFalseTest() {
        Tuple<String, Integer> tuple1 = new Tuple<>("Key1", 123);
        Tuple<String, Integer> tuple2 = new Tuple<>("Key2", 123);
        Tuple<String, Integer> tuple3 = new Tuple<>("Key1", 124);

        assertThat(tuple1.equals(tuple2)).isFalse();
        assertThat(tuple1.equals(tuple3)).isFalse();
    }
}
