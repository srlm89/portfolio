package xxl.java.jooq.test;

import xxl.java.jooq.Jooq;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.function.Consumer;

public class JooqTestRollback {

    private DSLContext dsl = Jooq.dsl();

    public DSLContext dsl() {
        return dsl;
    }

    public void testOn(Consumer<DSLContext> call) {
        boolean[] testSuccess = new boolean[] { false };
        try {
            dsl.transactionResult(conf -> {
                call.accept(DSL.using(conf));
                testSuccess[0] = true;
                throw new RuntimeException("Rollback exception");
            });
        }
        catch (Exception e) {
            if (testSuccess[0] && e.getMessage().equals("Rollback exception")) {
                return;
            }
            throw e;
        }
    }
}
