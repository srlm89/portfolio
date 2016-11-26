package xxl.java;

import xxl.java.jooq.test.JooqTestRollback;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static xxl.java.jooq.tables.Employee.EMPLOYEE;

public class MainTest extends JooqTestRollback {

    @Before
    public void tableStartsEmpty() {
        checkNumberOfElements(dsl(), 0);
    }

    @After
    public void tableEndsEmpty() {
        checkNumberOfElements(dsl(), 0);
    }

    @Test
    public void testActions() {
        Main.main(new String[] { "add", "McClure", "I beg celebrities for money" });
        checkNumberOfElements(dsl(), 1);
        Main.main(new String[] { "show", "McClure" });
        checkNumberOfElements(dsl(), 1);
        Main.main(new String[] { "delete", "McClure" });
    }

    @Test
    public void testAddEmployeeAndCheckRollback() {
        testOn(dsl -> {
            dsl.insertInto(EMPLOYEE, EMPLOYEE.NAME, EMPLOYEE.JOB_DESCRIPTION)
                    .values("Miller", "I step in front of cars and sue the drivers")
                    .execute();
            Result<Record2<Integer, String>> fetch = dsl.select(EMPLOYEE.ID, EMPLOYEE.NAME).from(EMPLOYEE).fetch();
            Assert.assertEquals(1, fetch.size());
            Assert.assertEquals("Miller", fetch.get(0).getValue(EMPLOYEE.NAME));
            Assert.assertFalse(null == fetch.get(0).getValue(EMPLOYEE.ID));
            checkNumberOfElements(dsl, 1);
        });
    }

    @Test
    public void testAddEmployeeFails() {
        try {
            testOn(dsl -> dsl.insertInto(EMPLOYEE, EMPLOYEE.NAME).values("Scarlett").execute());
            Assert.fail("Should have failed");
        }
        catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("null value in column \"job_description\""));
        }
    }

    private void checkNumberOfElements(DSLContext dsl, int size) {
        Result<Record1<Integer>> execute = dsl.select(EMPLOYEE.ID).from(EMPLOYEE).fetch();
        Assert.assertEquals(size, execute.size());
    }
}
