package com.github.embeditcz.dbadvisor.core;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.embeditcz.dbadvisor.core.internal.DbAdvisorConfiguration;
import org.springframework.context.annotation.Import;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ DbAdvisorConfiguration.class })
public @interface EnableDbAdvisor {
}
