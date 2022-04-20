package test;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class JNITest {
    static {
        System.loadLibrary("msvcrt");
    }

    native void printf(String format, Object... args);

    public static void main(String[] args) {
        new JNITest().printf("hello world");
    }
}
