package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.model.Employee;

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
