package fh.technikum.sam.etc;

import fh.technikum.sam.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void removeTokenPrefixRegular() {
        String token = "Bearer 4";
        assertThat(Utils.removeTokenPrefix(token)).isEqualTo("4");
    }

    @Test
    public void removeTokenPrefixNoBearer() {
        String token = "4";
        assertThat(Utils.removeTokenPrefix(token)).isEqualTo("4");
    }

    @Test
    public void removeTokenPrefixRandomString() {
        String token = "asfdj1234123";
        assertThat(Utils.removeTokenPrefix(token)).isEqualTo(token);
    }

    @Test
    public void removeTokenPrefixNull() {
        assertThat(Utils.removeTokenPrefix(null)).isEqualTo("");
    }
}
