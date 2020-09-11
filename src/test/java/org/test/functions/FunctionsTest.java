package org.test.functions;

import org.junit.Test;
import org.test.DummyAdvice;
import org.test.factory.DefaultProxyFactory;
import org.test.model.Company;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.test.DummyFunction.dummy;
import static org.test.DummyJpqlStringWriter.asString;

public class FunctionsTest {
  @Test
  public void lower() {
    assertEquals("lower(A)", asString(new Lower("A")));
  }

  @Test
  public void upper() {
    assertEquals("upper(A)", asString(new Upper("A")));
  }

  @Test
  public void upperLower() {
    assertEquals("upper(lower(A))", asString(new Upper(new Lower("A"))));
  }

  @Test
  public void lowerUpper() {
    assertEquals("lower(upper(A))", asString(new Lower(new Upper("A"))));
  }

  @Test
  public void trim() {
    assertEquals("trim(A)", asString(new Trim("A")));
  }

  @Test
  public void trimWithChar() {
    assertEquals("trim(x from A)", asString(new Trim("A", 'x')));
  }

  @Test
  public void nestedTrim() {
    assertEquals("trim(lower(A))", asString(new Trim(new Lower("A"))));
  }

  @Test
  public void nestedTrimWithChar() {
    assertEquals("trim(x from lower(A))", asString(new Trim(new Lower("A"), 'x')));
  }

  @Test
  public void leftTrim() {
    assertEquals("trim(leading from A)", asString(new LeftTrim("A")));
  }

  @Test
  public void leftTrimWithChar() {
    assertEquals("trim(leading x from A)", asString(new LeftTrim("A", 'x')));
  }

  @Test
  public void nestedLeftTrim() {
    assertEquals("trim(leading from lower(A))", asString(new LeftTrim(new Lower("A"))));
  }

  @Test
  public void nestedLeftTrimWithChar() {
    assertEquals("trim(leading x from lower(A))", asString(new LeftTrim(new Lower("A"), 'x')));
  }

  @Test
  public void rightTrim() {
    assertEquals("trim(trailing from A)", asString(new RightTrim("A")));
  }

  @Test
  public void rightTrimWithChar() {
    assertEquals("trim(trailing x from A)", asString(new RightTrim("A", 'x')));
  }

  @Test
  public void nestedRightTrim() {
    assertEquals("trim(trailing from lower(A))", asString(new RightTrim(new Lower("A"))));
  }

  @Test
  public void nestedRightTrimWithChar() {
    assertEquals("trim(trailing x from lower(A))", asString(new RightTrim(new Lower("A"), 'x')));
  }

  @Test
  public void concatSingle() {
    assertEquals("concat(A)", asString(new Concat("A")));
  }

  @Test
  public void concatSingleNested() {
    assertEquals("concat(lower(A))", asString(new Concat(new Lower("A"))));
  }

  @Test
  public void concatMulti() {
    assertEquals("concat(A, B, C)", asString(new Concat("A", "B", "C")));
  }

  @Test
  public void concatMultiNested() {
    assertEquals("concat(lower(A), upper(B))", asString(new Concat(new Lower("A"), new Upper("B"))));
  }

  @Test
  public void substringNoLength() {
    assertEquals("substring(A, 1)", asString(new Substring("A", 1)));
  }

  @Test
  public void substringNoLengthNested() {
    assertEquals("substring(lower(A), 1)", asString(new Substring(new Lower("A"), 1)));
    assertEquals("substring(A, dummy(1))", asString(new Substring("A", dummy(1))));
    assertEquals("substring(lower(A), dummy(1))", asString(new Substring(new Lower("A"), dummy(1))));
  }

  @Test
  public void substring() {
    assertEquals("substring(A, 1, 2)", asString(new Substring("A", 1, 2)));
  }

  @Test
  public void substringNested() {
    assertEquals("substring(A, dummy(1), 2)", asString(new Substring("A", dummy(1), 2)));
    assertEquals("substring(A, 1, dummy(2))", asString(new Substring("A", 1, dummy(2))));
    assertEquals("substring(A, dummy(1), dummy(2))", asString(new Substring("A", dummy(1), dummy(2))));
    assertEquals("substring(lower(A), 1, 2)", asString(new Substring(new Lower("A"), 1, 2)));
    assertEquals("substring(lower(A), dummy(1), 2)", asString(new Substring(new Lower("A"), dummy(1), 2)));
    assertEquals("substring(lower(A), 1, dummy(2))", asString(new Substring(new Lower("A"), 1, dummy(2))));
    assertEquals("substring(lower(A), dummy(1), dummy(2))", asString(new Substring(new Lower("A"), dummy(1), dummy(2))));
  }

  @Test
  public void length() {
    assertEquals("length(A)", asString(new Length("A")));
  }

  @Test
  public void lengthNested() {
    assertEquals("length(lower(A))", asString(new Length(new Lower("A"))));
  }

  @Test
  public void locateNoPosition() {
    assertEquals("locate(A, B)", asString(new Locate("A", "B")));
  }

  @Test
  public void locateNoPositionNested() {
    assertEquals("locate(lower(A), B)", asString(new Locate(new Lower("A"), "B")));
    assertEquals("locate(A, lower(B))", asString(new Locate("A", new Lower("B"))));
    assertEquals("locate(lower(A), lower(B))", asString(new Locate(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void locate() {
    assertEquals("locate(A, B, 1)", asString(new Locate("A", "B", 1)));
  }

  @Test
  public void locateNested() {
    assertEquals("locate(A, lower(B), 1)", asString(new Locate("A", new Lower("B"), 1)));
    assertEquals("locate(A, B, dummy(1))", asString(new Locate("A", "B", dummy(1))));
    assertEquals("locate(A, lower(B), dummy(1))", asString(new Locate("A", new Lower("B"), dummy(1))));
    assertEquals("locate(lower(A), B, 1)", asString(new Locate(new Lower("A"), "B", 1)));
    assertEquals("locate(lower(A), lower(B), 1)", asString(new Locate(new Lower("A"), new Lower("B"), 1)));
    assertEquals("locate(lower(A), B, dummy(1))", asString(new Locate(new Lower("A"), "B", dummy(1))));
    assertEquals("locate(lower(A), lower(B), dummy(1))", asString(new Locate(new Lower("A"), new Lower("B"), dummy(1))));
  }

  @Test
  public void add() {
    assertEquals("1 + 2", asString(new Add<>(1, 2)));
    assertEquals("1 + 2", asString(new Add<>(1, 2L)));
    assertEquals("1 + 2.3", asString(new Add<>(1, 2.3)));
  }

  @Test
  public void addNested() {
    assertEquals("length(A) + 2", asString(new Add<>(new Length("A"), 2)));
    assertEquals("1.2 + length(B)", asString(new Add<>(1.2, new Length("B"))));
    assertEquals("1 + 2 + 3 + 4", asString(new Add<>(new Add<>(1, 2), new Add<>(3, 4))));
    assertEquals("1 + 2 + 3 - 4", asString(new Add<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("1 + 2 + 3 * 4", asString(new Add<>(new Add<>(1, 2), new Multi<>(3, 4))));
    assertEquals("1 + 2 + 3 / 4", asString(new Add<>(new Add<>(1, 2), new Div<>(3, 4))));
  }

  @Test
  public void sub() {
    assertEquals("1 - 2", asString(new Sub<>(1, 2)));
    assertEquals("1 - 2", asString(new Sub<>(1, 2L)));
    assertEquals("1 - 2.3", asString(new Sub<>(1, 2.3)));
  }

  @Test
  public void subNested() {
    assertEquals("length(A) - 2", asString(new Sub<>(new Length("A"), 2)));
    assertEquals("1.2 - length(B)", asString(new Sub<>(1.2, new Length("B"))));
    assertEquals("1 + 2 - (3 + 4)", asString(new Sub<>(new Add<>(1, 2), new Add<>(3, 4))));
    assertEquals("1 + 2 - (3 - 4)", asString(new Sub<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("1 + 2 - 3 * 4", asString(new Sub<>(new Add<>(1, 2), new Multi<>(3, 4))));
    assertEquals("1 + 2 - 3 / 4", asString(new Sub<>(new Add<>(1, 2), new Div<>(3, 4))));
  }

  @Test
  public void multi() {
    assertEquals("1 * 2", asString(new Multi<>(1, 2)));
    assertEquals("1 * 2", asString(new Multi<>(1, 2L)));
    assertEquals("1 * 2.3", asString(new Multi<>(1, 2.3)));
  }

  @Test
  public void multiNested() {
    assertEquals("length(A) * 2", asString(new Multi<>(new Length("A"), 2)));
    assertEquals("1.2 * length(B)", asString(new Multi<>(1.2, new Length("B"))));
    assertEquals("(1 + 2) * (3 - 4)", asString(new Multi<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("1 * 2 * (3 / 4)", asString(new Multi<>(new Multi<>(1, 2), new Div<>(3, 4))));
    assertEquals("1 * 2 * 3 * 4", asString(new Multi<>(new Multi<>(1, 2), new Multi<>(3, 4))));
  }

  @Test
  public void div() {
    assertEquals("1 / 2", asString(new Div<>(1, 2)));
    assertEquals("1 / 2", asString(new Div<>(1, 2L)));
    assertEquals("1 / 2.3", asString(new Div<>(1, 2.3)));
  }

  @Test
  public void divNested() {
    assertEquals("length(A) / 2", asString(new Div<>(new Length("A"), 2)));
    assertEquals("1.2 / length(B)", asString(new Div<>(1.2, new Length("B"))));
    assertEquals("(1 + 2) / (3 - 4)", asString(new Div<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("(1 / 2) / (3 * 4)", asString(new Div<>(new Div<>(1, 2), new Multi<>(3, 4))));
  }

  @Test
  public void abs() {
    assertEquals("abs(1)", asString(new Abs<>(1)));
  }

  @Test
  public void absNested() {
    assertEquals("abs(1 - 2)", asString(new Abs<>(new Sub<>(1, 2))));
  }

  @Test
  public void mod() {
    assertEquals("mod(10 / 20)", asString(new Mod<>(new Div<>(10, 20))));
  }

  @Test
  public void sqrt() {
    assertEquals("sqrt(1)", asString(new Sqrt<>(1)));
  }

  @Test
  public void sqrtNested() {
    assertEquals("sqrt(1 + 2)", asString(new Sqrt<>(new Add<>(1, 2))));
  }

  @Test
  public void currentDate() {
    assertEquals("current_date", asString(new CurrentDate()));
  }

  @Test
  public void currentTime() {
    assertEquals("current_time", asString(new CurrentTime()));
  }

  @Test
  public void currentTimestamp() {
    assertEquals("current_timestamp", asString(new CurrentTimestamp()));
  }

  @Test
  public void caseTest() {
    assertEquals(
        "case 1 when 2 then 3 when 4 then 5 else 6 end",
        asString(new Case<>(1).when(2).then(3).when(4).then(5).orElse(6))
    );
  }

  @Test
  public void caseNestedTest() {
    assertEquals(
        "case 1 + 2 when 1 + 2 then 3 + 4 when 5 + 6 then 7 + 8 else 9 + 10 end",
        asString(new Case<>(new Add<>(1, 2)).when(new Add<>(1, 2)).then(new Add<>(3, 4)).when(new Add<>(5, 6)).then(new Add<>(7, 8)).orElse(new Add<>(9, 10)))
    );
  }

  @Test
  public void casePredicate() {
    assertEquals(
        "case when 1 = 2 then 3 when 4 = 5 then 6 else 7 end",
        asString(new CasePredicate().when(1).is(2).then(3).when(4).is(5).then(6).orElse(7))
    );
  }

  @Test
  public void casePredicateNested() {
    assertEquals(
        "case when 1 + 2 = 3 then 4 + 5 when 6 + 7 = 8 then 9 + 10 else 11 + 12 end",
        asString(new CasePredicate().when(new Add<>(1, 2)).is(3).then(new Add<>(4, 5)).when(new Add<>(6, 7)).is(8).then(new Add<>(9, 10)).orElse(new Add<>(11, 12)))
    );
  }

  @Test
  public void casePredicateCollection() {
    List<Object> list = new ArrayList<>();
    assertEquals(
        "case when [] is empty then 1 when [] is not empty then 2 else 0 end",
        asString(new CasePredicate().when(list).isEmpty().then(1).when(list).isNotEmpty().then(2).orElse(0))
    );
  }

  @Test
  public void coalesceSingle() {
    assertEquals("coalesce(A)", asString(new Coalesce<>("A")));
  }

  @Test
  public void coalesceSingleNested() {
    assertEquals("coalesce(lower(A))", asString(new Coalesce<>(new Lower("A"))));
  }

  @Test
  public void coalesceMulti() {
    assertEquals("coalesce(A, B)", asString(new Coalesce<>("A", "B")));
  }

  @Test
  public void coalesceMultiNested() {
    assertEquals("coalesce(lower(A), lower(B))", asString(new Coalesce<>(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void coalesceMixed() {
    assertEquals("coalesce(A, lower(B))", asString(new Coalesce<>("A").coalesce(new Lower("B"))));
    assertEquals("coalesce(lower(A), B)", asString(new Coalesce<>(new Lower("A")).coalesce("B")));
  }

  @Test
  public void nullif() {
    assertEquals("nullif(A, B)", asString(new Nullif<>("A", "B")));
  }

  @Test
  public void nullifNested() {
    assertEquals("nullif(lower(A), lower(B))", asString(new Nullif<>(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void cast() {
    assertEquals("cast(A byte)", asString(new Cast<>("A", Cast.Type.BYTE)));
    assertEquals("cast(A short)", asString(new Cast<>("A", Cast.Type.SHORT)));
    assertEquals("cast(A integer)", asString(new Cast<>("A", Cast.Type.INTEGER)));
    assertEquals("cast(A long)", asString(new Cast<>("A", Cast.Type.LONG)));
    assertEquals("cast(A float)", asString(new Cast<>("A", Cast.Type.FLOAT)));
    assertEquals("cast(A double)", asString(new Cast<>("A", Cast.Type.DOUBLE)));
    assertEquals("cast(A character)", asString(new Cast<>("A", Cast.Type.CHAR)));
    assertEquals("cast(A boolean)", asString(new Cast<>("A", Cast.Type.BOOLEAN)));
    assertEquals("cast(A yes_no)", asString(new Cast<>("A", Cast.Type.YES_NO)));
    assertEquals("cast(A true_false)", asString(new Cast<>("A", Cast.Type.TRUE_FALSE)));
    assertEquals("cast(A string)", asString(new Cast<>("A", Cast.Type.STRING)));
    assertEquals("cast(A date)", asString(new Cast<>("A", Cast.Type.DATE)));
    assertEquals("cast(A time)", asString(new Cast<>("A", Cast.Type.TIME)));
    assertEquals("cast(A timestamp)", asString(new Cast<>("A", Cast.Type.TIMESTAMP)));
    assertEquals("cast(A calendar)", asString(new Cast<>("A", Cast.Type.CALENDAR)));
    assertEquals("cast(A calendar_date)", asString(new Cast<>("A", Cast.Type.CALENDAR_DATE)));
    assertEquals("cast(A big_decimal)", asString(new Cast<>("A", Cast.Type.BIG_DECIMAL)));
    assertEquals("cast(A big_integer)", asString(new Cast<>("A", Cast.Type.BIG_INTEGER)));
    assertEquals("cast(A locale)", asString(new Cast<>("A", Cast.Type.LOCALE)));
    assertEquals("cast(A timezone)", asString(new Cast<>("A", Cast.Type.TIME_ZONE)));
    assertEquals("cast(A currency)", asString(new Cast<>("A", Cast.Type.CURRENCY)));
    assertEquals("cast(A class)", asString(new Cast<>("A", Cast.Type.CLASS)));
    assertEquals("cast(A binary)", asString(new Cast<>("A", Cast.Type.BINARY)));
    assertEquals("cast(A text)", asString(new Cast<>("A", Cast.Type.TEXT)));
    assertEquals("cast(A serializable)", asString(new Cast<>("A", Cast.Type.SERIALIZABLE)));
    assertEquals("cast(A clob)", asString(new Cast<>("A", Cast.Type.CLOB)));
    assertEquals("cast(A blob)", asString(new Cast<>("A", Cast.Type.BLOB)));
    assertEquals("cast(A imm_date)", asString(new Cast<>("A", Cast.Type.IMM_DATE)));
    assertEquals("cast(A imm_time)", asString(new Cast<>("A", Cast.Type.IMM_TIME)));
    assertEquals("cast(A imm_timestamp)", asString(new Cast<>("A", Cast.Type.IMM_TIMESTAMP)));
    assertEquals("cast(A imm_calendar)", asString(new Cast<>("A", Cast.Type.IMM_CALENDAR)));
    assertEquals("cast(A imm_calendar_date)", asString(new Cast<>("A", Cast.Type.IMM_CALENDAR_DATE)));
    assertEquals("cast(A imm_serializable)", asString(new Cast<>("A", Cast.Type.IMM_SERIALIZABLE)));
    assertEquals("cast(A imm_binary)", asString(new Cast<>("A", Cast.Type.IMM_BINARY)));
  }

  @Test
  public void castNested() {
    assertEquals("cast(lower(A) text)", asString(new Cast<>(new Lower("A"), Cast.Type.TEXT)));
  }

  @Test
  public void extract() {
    Date date = Date.from(LocalDate.of(2020, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant());

    assertEquals("extract(YEAR from 2020-01-01)", asString(new Extract(date, Extract.Part.YEAR)));
    assertEquals("extract(MONTH from 2020-01-01)", asString(new Extract(date, Extract.Part.MONTH)));
    assertEquals("extract(DAY from 2020-01-01)", asString(new Extract(date, Extract.Part.DAY)));
    assertEquals("extract(HOUR from 2020-01-01)", asString(new Extract(date, Extract.Part.HOUR)));
    assertEquals("extract(MINUTE from 2020-01-01)", asString(new Extract(date, Extract.Part.MINUTE)));
    assertEquals("extract(SECOND from 2020-01-01)", asString(new Extract(date, Extract.Part.SECOND)));
  }

  @Test
  public void extractNested() {
    assertEquals("extract(YEAR from current_date)", asString(new Extract(new CurrentDate(), Extract.Part.YEAR)));
  }

  @Test
  public void regexp() {
    assertEquals("A regexp B", asString(new RegExp("A", "B")));
  }

  @Test
  public void regexpNested() {
    assertEquals("lower(A) regexp B", asString(new RegExp(new Lower("A"), "B")));
  }

  @Test
  public void index() {
    Company company = new DefaultProxyFactory().createProxy(Company.class, new DummyAdvice());
    assertEquals("index(Company)", asString(new Index(company)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void indexNonProxy() {
    assertEquals("index(Company)", asString(new Index(new Company())));
  }

  @Test(expected = IllegalArgumentException.class)
  public void indexNonEntity() {
    TestEntity testEntity = new DefaultProxyFactory().createProxy(TestEntity.class, new DummyAdvice());
    assertEquals("index(TestEntity)", asString(new Index(testEntity)));
  }

  @Test
  public void key() {
    assertEquals("key({})", asString(new Key<>(new HashMap<>())));
  }

  @Test
  public void size() {
    assertEquals("size([])", asString(new Size(new ArrayList<>())));
  }

  @Test
  public void type() {
    Company company = new DefaultProxyFactory().createProxy(Company.class, new DummyAdvice());
    assertEquals("type(Company)", asString(new Type(company)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void typeNonProxy() {
    assertEquals("type(Company)", asString(new Type(new Company())));
  }

  @Test(expected = IllegalArgumentException.class)
  public void typeNonEntity() {
    TestEntity testEntity = new DefaultProxyFactory().createProxy(TestEntity.class, new DummyAdvice());
    assertEquals("type(TestEntity)", asString(new Type(testEntity)));
  }

  public static class TestEntity {
  }
}
