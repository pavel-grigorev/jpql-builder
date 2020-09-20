package org.pavel.jpqlbuilder.samples;

import org.junit.Test;
import org.pavel.jpqlbuilder.model.Employee;
import org.pavel.jpqlbuilder.JpqlBuilder;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MinimalTest {
  @Test
  public void minimal() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);

    assertEquals("select a from test_Employee a", select.getQueryString());
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }
}
