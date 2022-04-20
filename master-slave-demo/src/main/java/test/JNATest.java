package test;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class JNATest {

    public interface CLib extends Library {
        CLib C_LIB = Native.load((Platform.isWindows() ? "msvcrt" : "c"), CLib.class);

        void printf(String format, Object... args);
    }

    public static void main(String[] args) {
        CLib.C_LIB.printf("hello world");
    }
}
