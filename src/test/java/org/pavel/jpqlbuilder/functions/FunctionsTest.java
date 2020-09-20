package org.pavel.jpqlbuilder.functions;

import org.junit.Assert;
import org.junit.Test;
import org.pavel.jpqlbuilder.DummyAdvice;
import org.pavel.jpqlbuilder.DummyFunction;
import org.pavel.jpqlbuilder.DummyJpqlStringWriter;
import org.pavel.jpqlbuilder.factory.DefaultProxyFactory;
import org.pavel.jpqlbuilder.model.Company;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FunctionsTest {
  @Test
  public void lower() {
    Assert.assertEquals("lower(A)", DummyJpqlStringWriter.asString(new Lower("A")));
  }

  @Test
  public void upper() {
    Assert.assertEquals("upper(A)", DummyJpqlStringWriter.asString(new Upper("A")));
  }

  @Test
  public void upperLower() {
    Assert.assertEquals("upper(lower(A))", DummyJpqlStringWriter.asString(new Upper(new Lower("A"))));
  }

  @Test
  public void lowerUpper() {
    Assert.assertEquals("lower(upper(A))", DummyJpqlStringWriter.asString(new Lower(new Upper("A"))));
  }

  @Test
  public void trim() {
    Assert.assertEquals("trim(A)", DummyJpqlStringWriter.asString(new Trim("A")));
  }

  @Test
  public void trimWithChar() {
    Assert.assertEquals("trim(x from A)", DummyJpqlStringWriter.asString(new Trim("A", 'x')));
  }

  @Test
  public void nestedTrim() {
    Assert.assertEquals("trim(lower(A))", DummyJpqlStringWriter.asString(new Trim(new Lower("A"))));
  }

  @Test
  public void nestedTrimWithChar() {
    Assert.assertEquals("trim(x from lower(A))", DummyJpqlStringWriter.asString(new Trim(new Lower("A"), 'x')));
  }

  @Test
  public void leftTrim() {
    Assert.assertEquals("trim(leading from A)", DummyJpqlStringWriter.asString(new LeftTrim("A")));
  }

  @Test
  public void leftTrimWithChar() {
    Assert.assertEquals("trim(leading x from A)", DummyJpqlStringWriter.asString(new LeftTrim("A", 'x')));
  }

  @Test
  public void nestedLeftTrim() {
    Assert.assertEquals("trim(leading from lower(A))", DummyJpqlStringWriter.asString(new LeftTrim(new Lower("A"))));
  }

  @Test
  public void nestedLeftTrimWithChar() {
    Assert.assertEquals("trim(leading x from lower(A))", DummyJpqlStringWriter.asString(new LeftTrim(new Lower("A"), 'x')));
  }

  @Test
  public void rightTrim() {
    Assert.assertEquals("trim(trailing from A)", DummyJpqlStringWriter.asString(new RightTrim("A")));
  }

  @Test
  public void rightTrimWithChar() {
    Assert.assertEquals("trim(trailing x from A)", DummyJpqlStringWriter.asString(new RightTrim("A", 'x')));
  }

  @Test
  public void nestedRightTrim() {
    Assert.assertEquals("trim(trailing from lower(A))", DummyJpqlStringWriter.asString(new RightTrim(new Lower("A"))));
  }

  @Test
  public void nestedRightTrimWithChar() {
    Assert.assertEquals("trim(trailing x from lower(A))", DummyJpqlStringWriter.asString(new RightTrim(new Lower("A"), 'x')));
  }

  @Test
  public void concatSingle() {
    Assert.assertEquals("concat(A)", DummyJpqlStringWriter.asString(new Concat("A")));
  }

  @Test
  public void concatSingleNested() {
    Assert.assertEquals("concat(lower(A))", DummyJpqlStringWriter.asString(new Concat(new Lower("A"))));
  }

  @Test
  public void concatMulti() {
    Assert.assertEquals("concat(A, B, C)", DummyJpqlStringWriter.asString(new Concat("A", "B", "C")));
  }

  @Test
  public void concatMultiNested() {
    Assert.assertEquals("concat(lower(A), upper(B))", DummyJpqlStringWriter.asString(new Concat(new Lower("A"), new Upper("B"))));
  }

  @Test
  public void substringNoLength() {
    Assert.assertEquals("substring(A, 1)", DummyJpqlStringWriter.asString(new Substring("A", 1)));
  }

  @Test
  public void substringNoLengthNested() {
    Assert.assertEquals("substring(lower(A), 1)", DummyJpqlStringWriter.asString(new Substring(new Lower("A"), 1)));
    Assert.assertEquals("substring(A, dummy(1))", DummyJpqlStringWriter.asString(new Substring("A", DummyFunction.dummy(1))));
    Assert.assertEquals("substring(lower(A), dummy(1))", DummyJpqlStringWriter.asString(new Substring(new Lower("A"), DummyFunction.dummy(1))));
  }

  @Test
  public void substring() {
    Assert.assertEquals("substring(A, 1, 2)", DummyJpqlStringWriter.asString(new Substring("A", 1, 2)));
  }

  @Test
  public void substringNested() {
    Assert.assertEquals("substring(A, dummy(1), 2)", DummyJpqlStringWriter.asString(new Substring("A", DummyFunction.dummy(1), 2)));
    Assert.assertEquals("substring(A, 1, dummy(2))", DummyJpqlStringWriter.asString(new Substring("A", 1, DummyFunction.dummy(2))));
    Assert.assertEquals("substring(A, dummy(1), dummy(2))", DummyJpqlStringWriter.asString(new Substring("A", DummyFunction.dummy(1), DummyFunction.dummy(2))));
    Assert.assertEquals("substring(lower(A), 1, 2)", DummyJpqlStringWriter.asString(new Substring(new Lower("A"), 1, 2)));
    Assert.assertEquals("substring(lower(A), dummy(1), 2)", DummyJpqlStringWriter.asString(new Substring(new Lower("A"), DummyFunction.dummy(1), 2)));
    Assert.assertEquals("substring(lower(A), 1, dummy(2))", DummyJpqlStringWriter.asString(new Substring(new Lower("A"), 1, DummyFunction.dummy(2))));
    Assert.assertEquals("substring(lower(A), dummy(1), dummy(2))", DummyJpqlStringWriter.asString(new Substring(new Lower("A"), DummyFunction.dummy(1), DummyFunction.dummy(2))));
  }

  @Test
  public void length() {
    Assert.assertEquals("length(A)", DummyJpqlStringWriter.asString(new Length("A")));
  }

  @Test
  public void lengthNested() {
    Assert.assertEquals("length(lower(A))", DummyJpqlStringWriter.asString(new Length(new Lower("A"))));
  }

  @Test
  public void locateNoPosition() {
    Assert.assertEquals("locate(A, B)", DummyJpqlStringWriter.asString(new Locate("A", "B")));
  }

  @Test
  public void locateNoPositionNested() {
    Assert.assertEquals("locate(lower(A), B)", DummyJpqlStringWriter.asString(new Locate(new Lower("A"), "B")));
    Assert.assertEquals("locate(A, lower(B))", DummyJpqlStringWriter.asString(new Locate("A", new Lower("B"))));
    Assert.assertEquals("locate(lower(A), lower(B))", DummyJpqlStringWriter.asString(new Locate(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void locate() {
    Assert.assertEquals("locate(A, B, 1)", DummyJpqlStringWriter.asString(new Locate("A", "B", 1)));
  }

  @Test
  public void locateNested() {
    Assert.assertEquals("locate(A, lower(B), 1)", DummyJpqlStringWriter.asString(new Locate("A", new Lower("B"), 1)));
    Assert.assertEquals("locate(A, B, dummy(1))", DummyJpqlStringWriter.asString(new Locate("A", "B", DummyFunction.dummy(1))));
    Assert.assertEquals("locate(A, lower(B), dummy(1))", DummyJpqlStringWriter.asString(new Locate("A", new Lower("B"), DummyFunction.dummy(1))));
    Assert.assertEquals("locate(lower(A), B, 1)", DummyJpqlStringWriter.asString(new Locate(new Lower("A"), "B", 1)));
    Assert.assertEquals("locate(lower(A), lower(B), 1)", DummyJpqlStringWriter.asString(new Locate(new Lower("A"), new Lower("B"), 1)));
    Assert.assertEquals("locate(lower(A), B, dummy(1))", DummyJpqlStringWriter.asString(new Locate(new Lower("A"), "B", DummyFunction.dummy(1))));
    Assert.assertEquals("locate(lower(A), lower(B), dummy(1))", DummyJpqlStringWriter.asString(new Locate(new Lower("A"), new Lower("B"), DummyFunction.dummy(1))));
  }

  @Test
  public void add() {
    Assert.assertEquals("1 + 2", DummyJpqlStringWriter.asString(new Add<>(1, 2)));
    Assert.assertEquals("1 + 2", DummyJpqlStringWriter.asString(new Add<>(1, 2L)));
    Assert.assertEquals("1 + 2.3", DummyJpqlStringWriter.asString(new Add<>(1, 2.3)));
  }

  @Test
  public void addNested() {
    Assert.assertEquals("length(A) + 2", DummyJpqlStringWriter.asString(new Add<>(new Length("A"), 2)));
    Assert.assertEquals("1.2 + length(B)", DummyJpqlStringWriter.asString(new Add<>(1.2, new Length("B"))));
    Assert.assertEquals("1 + 2 + 3 + 4", DummyJpqlStringWriter.asString(new Add<>(new Add<>(1, 2), new Add<>(3, 4))));
    Assert.assertEquals("1 + 2 + 3 - 4", DummyJpqlStringWriter.asString(new Add<>(new Add<>(1, 2), new Sub<>(3, 4))));
    Assert.assertEquals("1 + 2 + 3 * 4", DummyJpqlStringWriter.asString(new Add<>(new Add<>(1, 2), new Multi<>(3, 4))));
    Assert.assertEquals("1 + 2 + 3 / 4", DummyJpqlStringWriter.asString(new Add<>(new Add<>(1, 2), new Div<>(3, 4))));
  }

  @Test
  public void sub() {
    Assert.assertEquals("1 - 2", DummyJpqlStringWriter.asString(new Sub<>(1, 2)));
    Assert.assertEquals("1 - 2", DummyJpqlStringWriter.asString(new Sub<>(1, 2L)));
    Assert.assertEquals("1 - 2.3", DummyJpqlStringWriter.asString(new Sub<>(1, 2.3)));
  }

  @Test
  public void subNested() {
    Assert.assertEquals("length(A) - 2", DummyJpqlStringWriter.asString(new Sub<>(new Length("A"), 2)));
    Assert.assertEquals("1.2 - length(B)", DummyJpqlStringWriter.asString(new Sub<>(1.2, new Length("B"))));
    Assert.assertEquals("1 + 2 - (3 + 4)", DummyJpqlStringWriter.asString(new Sub<>(new Add<>(1, 2), new Add<>(3, 4))));
    Assert.assertEquals("1 + 2 - (3 - 4)", DummyJpqlStringWriter.asString(new Sub<>(new Add<>(1, 2), new Sub<>(3, 4))));
    Assert.assertEquals("1 + 2 - 3 * 4", DummyJpqlStringWriter.asString(new Sub<>(new Add<>(1, 2), new Multi<>(3, 4))));
    Assert.assertEquals("1 + 2 - 3 / 4", DummyJpqlStringWriter.asString(new Sub<>(new Add<>(1, 2), new Div<>(3, 4))));
  }

  @Test
  public void multi() {
    Assert.assertEquals("1 * 2", DummyJpqlStringWriter.asString(new Multi<>(1, 2)));
    Assert.assertEquals("1 * 2", DummyJpqlStringWriter.asString(new Multi<>(1, 2L)));
    Assert.assertEquals("1 * 2.3", DummyJpqlStringWriter.asString(new Multi<>(1, 2.3)));
  }

  @Test
  public void multiNested() {
    Assert.assertEquals("length(A) * 2", DummyJpqlStringWriter.asString(new Multi<>(new Length("A"), 2)));
    Assert.assertEquals("1.2 * length(B)", DummyJpqlStringWriter.asString(new Multi<>(1.2, new Length("B"))));
    Assert.assertEquals("(1 + 2) * (3 - 4)", DummyJpqlStringWriter.asString(new Multi<>(new Add<>(1, 2), new Sub<>(3, 4))));
    Assert.assertEquals("1 * 2 * (3 / 4)", DummyJpqlStringWriter.asString(new Multi<>(new Multi<>(1, 2), new Div<>(3, 4))));
    Assert.assertEquals("1 * 2 * 3 * 4", DummyJpqlStringWriter.asString(new Multi<>(new Multi<>(1, 2), new Multi<>(3, 4))));
  }

  @Test
  public void div() {
    Assert.assertEquals("1 / 2", DummyJpqlStringWriter.asString(new Div<>(1, 2)));
    Assert.assertEquals("1 / 2", DummyJpqlStringWriter.asString(new Div<>(1, 2L)));
    Assert.assertEquals("1 / 2.3", DummyJpqlStringWriter.asString(new Div<>(1, 2.3)));
  }

  @Test
  public void divNested() {
    Assert.assertEquals("length(A) / 2", DummyJpqlStringWriter.asString(new Div<>(new Length("A"), 2)));
    Assert.assertEquals("1.2 / length(B)", DummyJpqlStringWriter.asString(new Div<>(1.2, new Length("B"))));
    Assert.assertEquals("(1 + 2) / (3 - 4)", DummyJpqlStringWriter.asString(new Div<>(new Add<>(1, 2), new Sub<>(3, 4))));
    Assert.assertEquals("(1 / 2) / (3 * 4)", DummyJpqlStringWriter.asString(new Div<>(new Div<>(1, 2), new Multi<>(3, 4))));
  }

  @Test
  public void abs() {
    Assert.assertEquals("abs(1)", DummyJpqlStringWriter.asString(new Abs<>(1)));
  }

  @Test
  public void absNested() {
    Assert.assertEquals("abs(1 - 2)", DummyJpqlStringWriter.asString(new Abs<>(new Sub<>(1, 2))));
  }

  @Test
  public void mod() {
    Assert.assertEquals("mod(10 / 20)", DummyJpqlStringWriter.asString(new Mod<>(new Div<>(10, 20))));
  }

  @Test
  public void sqrt() {
    Assert.assertEquals("sqrt(1)", DummyJpqlStringWriter.asString(new Sqrt<>(1)));
  }

  @Test
  public void sqrtNested() {
    Assert.assertEquals("sqrt(1 + 2)", DummyJpqlStringWriter.asString(new Sqrt<>(new Add<>(1, 2))));
  }

  @Test
  public void currentDate() {
    Assert.assertEquals("current_date", DummyJpqlStringWriter.asString(new CurrentDate()));
  }

  @Test
  public void currentTime() {
    Assert.assertEquals("current_time", DummyJpqlStringWriter.asString(new CurrentTime()));
  }

  @Test
  public void currentTimestamp() {
    Assert.assertEquals("current_timestamp", DummyJpqlStringWriter.asString(new CurrentTimestamp()));
  }

  @Test
  public void caseTest() {
    Assert.assertEquals(
        "case 1 when 2 then 3 when 4 then 5 else 6 end",
        DummyJpqlStringWriter.asString(new Case<>(1).when(2).then(3).when(4).then(5).orElse(6))
    );
  }

  @Test
  public void caseNestedTest() {
    Assert.assertEquals(
        "case 1 + 2 when 1 + 2 then 3 + 4 when 5 + 6 then 7 + 8 else 9 + 10 end",
        DummyJpqlStringWriter.asString(new Case<>(new Add<>(1, 2)).when(new Add<>(1, 2)).then(new Add<>(3, 4)).when(new Add<>(5, 6)).then(new Add<>(7, 8)).orElse(new Add<>(9, 10)))
    );
  }

  @Test
  public void casePredicate() {
    Assert.assertEquals(
        "case when 1 = 2 then 3 when 4 = 5 then 6 else 7 end",
        DummyJpqlStringWriter.asString(new CasePredicate().when(1).is(2).then(3).when(4).is(5).then(6).orElse(7))
    );
  }

  @Test
  public void casePredicateNested() {
    Assert.assertEquals(
        "case when 1 + 2 = 3 then 4 + 5 when 6 + 7 = 8 then 9 + 10 else 11 + 12 end",
        DummyJpqlStringWriter.asString(new CasePredicate().when(new Add<>(1, 2)).is(3).then(new Add<>(4, 5)).when(new Add<>(6, 7)).is(8).then(new Add<>(9, 10)).orElse(new Add<>(11, 12)))
    );
  }

  @Test
  public void casePredicateCollection() {
    List<Object> list = new ArrayList<>();
    Assert.assertEquals(
        "case when [] is empty then 1 when [] is not empty then 2 else 0 end",
        DummyJpqlStringWriter.asString(new CasePredicate().when(list).isEmpty().then(1).when(list).isNotEmpty().then(2).orElse(0))
    );
  }

  @Test
  public void coalesceSingle() {
    Assert.assertEquals("coalesce(A)", DummyJpqlStringWriter.asString(new Coalesce<>("A")));
  }

  @Test
  public void coalesceSingleNested() {
    Assert.assertEquals("coalesce(lower(A))", DummyJpqlStringWriter.asString(new Coalesce<>(new Lower("A"))));
  }

  @Test
  public void coalesceMulti() {
    Assert.assertEquals("coalesce(A, B)", DummyJpqlStringWriter.asString(new Coalesce<>("A", "B")));
  }

  @Test
  public void coalesceMultiNested() {
    Assert.assertEquals("coalesce(lower(A), lower(B))", DummyJpqlStringWriter.asString(new Coalesce<>(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void coalesceMixed() {
    Assert.assertEquals("coalesce(A, lower(B))", DummyJpqlStringWriter.asString(new Coalesce<>("A").coalesce(new Lower("B"))));
    Assert.assertEquals("coalesce(lower(A), B)", DummyJpqlStringWriter.asString(new Coalesce<>(new Lower("A")).coalesce("B")));
  }

  @Test
  public void nullif() {
    Assert.assertEquals("nullif(A, B)", DummyJpqlStringWriter.asString(new Nullif<>("A", "B")));
  }

  @Test
  public void nullifNested() {
    Assert.assertEquals("nullif(lower(A), lower(B))", DummyJpqlStringWriter.asString(new Nullif<>(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void cast() {
    Assert.assertEquals("cast(A byte)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.BYTE)));
    Assert.assertEquals("cast(A short)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.SHORT)));
    Assert.assertEquals("cast(A integer)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.INTEGER)));
    Assert.assertEquals("cast(A long)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.LONG)));
    Assert.assertEquals("cast(A float)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.FLOAT)));
    Assert.assertEquals("cast(A double)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.DOUBLE)));
    Assert.assertEquals("cast(A character)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.CHAR)));
    Assert.assertEquals("cast(A boolean)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.BOOLEAN)));
    Assert.assertEquals("cast(A yes_no)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.YES_NO)));
    Assert.assertEquals("cast(A true_false)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.TRUE_FALSE)));
    Assert.assertEquals("cast(A string)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.STRING)));
    Assert.assertEquals("cast(A date)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.DATE)));
    Assert.assertEquals("cast(A time)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.TIME)));
    Assert.assertEquals("cast(A timestamp)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.TIMESTAMP)));
    Assert.assertEquals("cast(A calendar)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.CALENDAR)));
    Assert.assertEquals("cast(A calendar_date)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.CALENDAR_DATE)));
    Assert.assertEquals("cast(A big_decimal)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.BIG_DECIMAL)));
    Assert.assertEquals("cast(A big_integer)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.BIG_INTEGER)));
    Assert.assertEquals("cast(A locale)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.LOCALE)));
    Assert.assertEquals("cast(A timezone)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.TIME_ZONE)));
    Assert.assertEquals("cast(A currency)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.CURRENCY)));
    Assert.assertEquals("cast(A class)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.CLASS)));
    Assert.assertEquals("cast(A binary)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.BINARY)));
    Assert.assertEquals("cast(A text)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.TEXT)));
    Assert.assertEquals("cast(A serializable)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.SERIALIZABLE)));
    Assert.assertEquals("cast(A clob)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.CLOB)));
    Assert.assertEquals("cast(A blob)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.BLOB)));
    Assert.assertEquals("cast(A imm_date)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_DATE)));
    Assert.assertEquals("cast(A imm_time)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_TIME)));
    Assert.assertEquals("cast(A imm_timestamp)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_TIMESTAMP)));
    Assert.assertEquals("cast(A imm_calendar)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_CALENDAR)));
    Assert.assertEquals("cast(A imm_calendar_date)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_CALENDAR_DATE)));
    Assert.assertEquals("cast(A imm_serializable)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_SERIALIZABLE)));
    Assert.assertEquals("cast(A imm_binary)", DummyJpqlStringWriter.asString(new Cast<>("A", Cast.Type.IMM_BINARY)));
  }

  @Test
  public void castNested() {
    Assert.assertEquals("cast(lower(A) text)", DummyJpqlStringWriter.asString(new Cast<>(new Lower("A"), Cast.Type.TEXT)));
  }

  @Test
  public void extract() {
    Date date = Date.from(LocalDate.of(2020, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant());

    Assert.assertEquals("extract(YEAR from 2020-01-01)", DummyJpqlStringWriter.asString(new Extract(date, Extract.Part.YEAR)));
    Assert.assertEquals("extract(MONTH from 2020-01-01)", DummyJpqlStringWriter.asString(new Extract(date, Extract.Part.MONTH)));
    Assert.assertEquals("extract(DAY from 2020-01-01)", DummyJpqlStringWriter.asString(new Extract(date, Extract.Part.DAY)));
    Assert.assertEquals("extract(HOUR from 2020-01-01)", DummyJpqlStringWriter.asString(new Extract(date, Extract.Part.HOUR)));
    Assert.assertEquals("extract(MINUTE from 2020-01-01)", DummyJpqlStringWriter.asString(new Extract(date, Extract.Part.MINUTE)));
    Assert.assertEquals("extract(SECOND from 2020-01-01)", DummyJpqlStringWriter.asString(new Extract(date, Extract.Part.SECOND)));
  }

  @Test
  public void extractNested() {
    Assert.assertEquals("extract(YEAR from current_date)", DummyJpqlStringWriter.asString(new Extract(new CurrentDate(), Extract.Part.YEAR)));
  }

  @Test
  public void regexp() {
    Assert.assertEquals("A regexp B", DummyJpqlStringWriter.asString(new RegExp("A", "B")));
  }

  @Test
  public void regexpNested() {
    Assert.assertEquals("lower(A) regexp B", DummyJpqlStringWriter.asString(new RegExp(new Lower("A"), "B")));
  }

  @Test
  public void index() {
    Company company = new DefaultProxyFactory().createProxy(Company.class, new DummyAdvice());
    Assert.assertEquals("index(Company)", DummyJpqlStringWriter.asString(new Index(company)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void indexNonProxy() {
    Assert.assertEquals("index(Company)", DummyJpqlStringWriter.asString(new Index(new Company())));
  }

  @Test(expected = IllegalArgumentException.class)
  public void indexNonEntity() {
    TestEntity testEntity = new DefaultProxyFactory().createProxy(TestEntity.class, new DummyAdvice());
    Assert.assertEquals("index(TestEntity)", DummyJpqlStringWriter.asString(new Index(testEntity)));
  }

  @Test
  public void key() {
    Assert.assertEquals("key({})", DummyJpqlStringWriter.asString(new Key<>(new HashMap<>())));
  }

  @Test
  public void size() {
    Assert.assertEquals("size([])", DummyJpqlStringWriter.asString(new Size(new ArrayList<>())));
  }

  @Test
  public void type() {
    Company company = new DefaultProxyFactory().createProxy(Company.class, new DummyAdvice());
    Assert.assertEquals("type(Company)", DummyJpqlStringWriter.asString(new Type(company)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void typeNonProxy() {
    Assert.assertEquals("type(Company)", DummyJpqlStringWriter.asString(new Type(new Company())));
  }

  @Test(expected = IllegalArgumentException.class)
  public void typeNonEntity() {
    TestEntity testEntity = new DefaultProxyFactory().createProxy(TestEntity.class, new DummyAdvice());
    Assert.assertEquals("type(TestEntity)", DummyJpqlStringWriter.asString(new Type(testEntity)));
  }

  @Test
  public void function() {
    List<String> arguments = Arrays.asList("A", "B");
    Assert.assertEquals("function('dummy', A, B)", DummyJpqlStringWriter.asString(new Function<>("dummy", arguments)));
  }

  @Test
  public void functionNested() {
    List<Object> arguments = Arrays.asList(DummyFunction.dummy("A"), DummyFunction.dummy("B"));
    Assert.assertEquals("function('dummy', dummy(A), dummy(B))", DummyJpqlStringWriter.asString(new Function<>("dummy", arguments)));
  }

  @Test
  public void functionNoArgs() {
    List<String> arguments = Collections.emptyList();
    Assert.assertEquals("function('dummy')", DummyJpqlStringWriter.asString(new Function<>("dummy", arguments)));
  }

  @Test(expected = NullPointerException.class)
  public void functionNullArgs() {
    new Function<>("dummy", null);
  }

  @Test(expected = NullPointerException.class)
  public void functionNullName() {
    new Function<>(null, Collections.emptyList());
  }

  @Test(expected = IllegalArgumentException.class)
  public void functionBadName() {
    new Function<>("'dummy", Collections.emptyList());
  }

  @Test
  public void func() {
    List<String> arguments = Arrays.asList("A", "B");
    Assert.assertEquals("func('dummy', A, B)", DummyJpqlStringWriter.asString(new Func<>("dummy", arguments)));
  }

  @Test
  public void funcNested() {
    List<Object> arguments = Arrays.asList(DummyFunction.dummy("A"), DummyFunction.dummy("B"));
    Assert.assertEquals("func('dummy', dummy(A), dummy(B))", DummyJpqlStringWriter.asString(new Func<>("dummy", arguments)));
  }

  @Test
  public void funcNoArgs() {
    List<String> arguments = Collections.emptyList();
    Assert.assertEquals("func('dummy')", DummyJpqlStringWriter.asString(new Func<>("dummy", arguments)));
  }

  @Test(expected = NullPointerException.class)
  public void funcNullArgs() {
    new Func<>("dummy", null);
  }

  @Test(expected = NullPointerException.class)
  public void funcNullName() {
    new Func<>(null, Collections.emptyList());
  }

  @Test(expected = IllegalArgumentException.class)
  public void funcBadName() {
    new Func<>("'dummy", Collections.emptyList());
  }

  @Test
  public void sql() {
    List<String> arguments = Arrays.asList("A", "B");
    Assert.assertEquals("sql('dummy', A, B)", DummyJpqlStringWriter.asString(new Sql<>("dummy", arguments)));
  }

  @Test
  public void sqlNested() {
    List<Object> arguments = Arrays.asList(DummyFunction.dummy("A"), DummyFunction.dummy("B"));
    Assert.assertEquals("sql('dummy', dummy(A), dummy(B))", DummyJpqlStringWriter.asString(new Sql<>("dummy", arguments)));
  }

  @Test
  public void sqlNoArgs() {
    List<String> arguments = Collections.emptyList();
    Assert.assertEquals("sql('(select sysdate from dual)')", DummyJpqlStringWriter.asString(new Sql<>("(select sysdate from dual)", arguments)));
  }

  @Test(expected = NullPointerException.class)
  public void sqlNullArgs() {
    new Sql<>("dummy", null);
  }

  @Test(expected = NullPointerException.class)
  public void sqlNullName() {
    new Sql<>(null, Collections.emptyList());
  }

  @Test(expected = IllegalArgumentException.class)
  public void sqlBadCode() {
    new Sql<>("'dummy", Collections.emptyList());
  }

  @Test
  public void column() {
    Company company = new DefaultProxyFactory().createProxy(Company.class, new DummyAdvice());
    Assert.assertEquals("column('dummy', Company)", DummyJpqlStringWriter.asString(new Column<>("dummy", company)));
  }

  @Test(expected = NullPointerException.class)
  public void columnNullName() {
    new Column<>(null, new Company());
  }

  @Test(expected = NullPointerException.class)
  public void columnNullEntity() {
    new Column<>("dummy", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void columnBadName() {
    Company company = new DefaultProxyFactory().createProxy(Company.class, new DummyAdvice());
    Assert.assertEquals("column('dummy', Company)", DummyJpqlStringWriter.asString(new Column<>("'dummy", company)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void columnNonEntity() {
    TestEntity testEntity = new DefaultProxyFactory().createProxy(TestEntity.class, new DummyAdvice());
    Assert.assertEquals("column('dummy', Company)", DummyJpqlStringWriter.asString(new Column<>("'dummy", testEntity)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void columnNonProxy() {
    Assert.assertEquals("column('dummy', Company)", DummyJpqlStringWriter.asString(new Column<>("'dummy", new Company())));
  }

  public static class TestEntity {
  }
}
