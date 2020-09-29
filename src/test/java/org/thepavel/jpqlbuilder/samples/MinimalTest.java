package org.thepavel.jpqlbuilder.samples;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.JpqlBuilder;

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
