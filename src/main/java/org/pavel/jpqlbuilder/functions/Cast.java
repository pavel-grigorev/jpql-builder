package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Cast<T> extends JpqlFunction<T> {
  private final Object argument;
  private final Type type;

  public Cast(Object argument, Type type) {
    this.argument = argument;
    this.type = type;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("cast(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(" ");
    stringWriter.appendString(type.name);
    stringWriter.appendString(")");
  }

  public enum Type {
    BYTE("byte"),
    SHORT("short"),
    INTEGER("integer"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    CHAR("character"),
    BOOLEAN("boolean"),
    YES_NO("yes_no"),
    TRUE_FALSE("true_false"),
    STRING("string"),
    DATE("date"),
    TIME("time"),
    TIMESTAMP("timestamp"),
    CALENDAR("calendar"),
    CALENDAR_DATE("calendar_date"),
    BIG_DECIMAL("big_decimal"),
    BIG_INTEGER("big_integer"),
    LOCALE("locale"),
    TIME_ZONE("timezone"),
    CURRENCY("currency"),
    CLASS("class"),
    BINARY("binary"),
    TEXT("text"),
    SERIALIZABLE("serializable"),
    CLOB("clob"),
    BLOB("blob"),
    IMM_DATE("imm_date"),
    IMM_TIME("imm_time"),
    IMM_TIMESTAMP("imm_timestamp"),
    IMM_CALENDAR("imm_calendar"),
    IMM_CALENDAR_DATE("imm_calendar_date"),
    IMM_SERIALIZABLE("imm_serializable"),
    IMM_BINARY("imm_binary");

    private final String name;

    Type(String name) {
      this.name = name;
    }
  }
}
