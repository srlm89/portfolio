package test.dynamic.compiler;

import java.util.List;
import java.util.LinkedList;

public class ListFactory {

    public List<String> getList() {
        return new LinkedList<String>();
    }
}

