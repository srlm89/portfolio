package xxl.java.jooq;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class Jooq {

    public static DSLContext dsl() {
        DefaultConfiguration jooqConfiguration = JooqConfigurationFactory.create();
        return DSL.using(jooqConfiguration);
    }
}
