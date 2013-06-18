package net.ogiekako.algorithm.utils.interfaces;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Overflowの場合に落ちてほしくないクラスにこのアノテーションをつける
 * cf. Matrix
 */

@Retention(RUNTIME)
public @interface NoCojacInstrumentation {
}
