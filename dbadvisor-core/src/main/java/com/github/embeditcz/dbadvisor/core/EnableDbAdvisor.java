package com.github.embeditcz.dbadvisor.core;

import com.github.embeditcz.dbadvisor.core.internal.DbAdvisorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation which enable advisor.
 * Usually is used on application entry point.
 *
 * <pre>{@code
 *    @literal @Configuration
 *    @literal @EnableDbAdvisor
 *     public class MyAppConfiguration {
 *     }
 * }</pre>
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ DbAdvisorConfiguration.class })
public @interface EnableDbAdvisor {
}
