package xxl.java;

import xxl.java.jooq.Jooq;
import xxl.java.jooq.tables.Employee;
import org.jooq.Record3;
import org.jooq.Result;

import java.util.Arrays;

import static xxl.java.jooq.tables.Employee.EMPLOYEE;
import static java.lang.String.*;

public class Main {

    public static void main(String[] args) {
        String action = args[0];
        if (action.equals("add")) {
            String name = args[1];
            String jobDescription = String.join(" ", Arrays.asList(args).subList(2, args.length));
            addEmployee(name, jobDescription);
        }
        else if (action.equals("show")) {
            showEmployee(args[1]);
        }
        else if (action.equals("delete")) {
            deleteEmployee(args[1]);
        }
    }

    public static void addEmployee(String name, String jobDescription) {
        int res = Jooq.dsl().insertInto(EMPLOYEE, EMPLOYEE.NAME, EMPLOYEE.JOB_DESCRIPTION).values(name, jobDescription).execute();
        if (res > 0) {
            System.out.println(format("Successfully added employee %s", name));
        }
    }

    public static void showEmployee(String name) {
        Result<Record3<Integer, String, String>> res = Jooq.dsl().select(EMPLOYEE.ID, EMPLOYEE.NAME, EMPLOYEE.JOB_DESCRIPTION)
                .from(EMPLOYEE)
                .where(EMPLOYEE.NAME.equal(name))
                .fetch();
        if (res.isEmpty()) {
            System.out.println(format("Employee %s not found", name));
        }
        else {
            System.out.println(format("%s\t%s\t%s",
                    res.get(0).getValue(EMPLOYEE.ID),
                    res.get(0).getValue(EMPLOYEE.NAME),
                    res.get(0).getValue(EMPLOYEE.JOB_DESCRIPTION)));
        }
    }

    public static void deleteEmployee(String name) {
        int res = Jooq.dsl().deleteFrom(EMPLOYEE).where(EMPLOYEE.NAME.equal(name)).execute();
        if (res > 0) {
            System.out.println(format("Successfully deleted employee %s", name));
        }
    }
}
