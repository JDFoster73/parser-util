/**
 * Copyright 2021 James David Foster jdfoster73@gmail.com
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package test.parserutil.vJSON;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import parserutil.impl.json.JSONArrayImpl;
import parserutil.impl.json.JSONField;
import parserutil.impl.json.JSONObjectImpl;
import parserutil.impl.json.JSONValueHolder;
import parserutil.impl.json.JSONValueImpl.VALTYPE;
import parserutil.impl.json.parser.JSONDocumentParser;
import parserutil.main.GeneralParserException;

/**
 * <p>JSON document parser function tests.  Make sure it correctly parses well-behaved input and
 * rejects ill-formed input.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONDocumentFunctionTests
{
  /**
   * <p>Test 1.  JSON input contains one string value.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test1_strvalue() throws IOException, GeneralParserException
  {
    String tststr = "\"This is a JSON test string value.\"";
    JSONDocumentParser jp = new JSONDocumentParser();
    //Value should be a single string.
    JSONValueHolder jsonVal = jp.parse(new StringReader(tststr));
    
    //Make sure the raw value corresponds to the test string.
    String rawstr = jsonVal.toString();
    assertTrue(rawstr.equals(tststr));

    //Make sure the element value corresponds to the test string less the quotes.
    String valstr = jsonVal.getValueType().getValue();
    String compstr = tststr.substring(1, tststr.length() - 1);
    assertTrue(valstr.equals(compstr));
  }
  
  /**
   * <p>Test 2.  JSON input contains one number value.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test2_numvalue() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    //All components
    String tststr = "-1234.56e-21";
    //Make sure parser throws no errors.
    JSONValueHolder jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));

    //Int
    tststr = "2342322";
    //Make sure parser throws no errors.
    jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));

    tststr = "-324532";
    //Make sure parser throws no errors.
    jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));

    //Float
    tststr = "2342322.3432";
    //Make sure parser throws no errors.
    jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));

    tststr = "-2342322.3432";
    //Make sure parser throws no errors.
    jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));
  }

  /**
   * <p>Test 3.  JSON input contains one boolean value.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test3_boolvalue() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    String tststr = "true";
    //Make sure parser throws no errors.
    JSONValueHolder jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));

    tststr = "false";
    //Make sure parser throws no errors.
    jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));
  }

  /**
   * <p>Test 4.  JSON input contains one null value.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test4_nullvalue() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    String tststr = "null";
    //Make sure parser throws no errors.
    JSONValueHolder jsonVal = jp.parse(new StringReader(tststr));
    assertTrue(jsonVal.getValueType().getValue().equals(tststr));
  }

  /**
   * <p>Test 5.  JSON input contains an array.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test5_array1() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    String tststr = "[\"string val\", 1231.421e-32, true, false, null, {\"field1\":133234.132}]";
    //Make sure parser throws no errors.
    JSONValueHolder jsonVal = jp.parse(new StringReader(tststr));
    JSONArrayImpl arrayType = jsonVal.getArrayType();
    assertTrue(arrayType.getField(0).getValueType().getValue().equals("string val"));
    assertTrue(arrayType.getField(1).getValueType().getValue().equals("1231.421e-32"));
    assertTrue(arrayType.getField(2).getValueType().getValue().equals("true"));
    assertTrue(arrayType.getField(3).getValueType().getValue().equals("false"));
    assertTrue(arrayType.getField(4).getValueType().getValue().equals("null"));
    
    //Object.
    JSONObjectImpl objectType = arrayType.getField(5).getObjectType();
    JSONField field = objectType.getField(0);
    assertTrue(field.getFieldName().equals("field1"));
    assertTrue(field.getValueType().getValue().equals("133234.132"));
    assertTrue(field.getValueType().getValueType() == VALTYPE.NUM_REA);
  }

  /**
   * <p>Test 6.  JSON input contains an object.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test6_obj1() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    String tststr = "{\"f1\":\"v1\", \"f2\":-1234.3234, \"f3\":true, \"f4\":false, \"f5\":null, \"f6\":[1234, true]}";
    //Make sure parser throws no errors.
    JSONValueHolder jsonVal = jp.parse(new StringReader(tststr));
    JSONObjectImpl objectType = jsonVal.getObjectType();
    assertTrue(objectType.getField(0).getValueType().getValue().equals("v1"));
    assertTrue(objectType.getField(1).getValueType().getValue().equals("-1234.3234"));
    assertTrue(objectType.getField(2).getValueType().getValue().equals("true"));
    assertTrue(objectType.getField(3).getValueType().getValue().equals("false"));
    assertTrue(objectType.getField(4).getValueType().getValue().equals("null"));
    
    //Object.
    JSONArrayImpl arrayType = objectType.getField(5).getArrayType();
    assertTrue(arrayType.getField(0).getValueType().getValue().equals("1234"));
    assertTrue(arrayType.getField(1).getValueType().getValue().equals("true"));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //ERROR CATCHING ASSERTIONS
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * <p>Test 101.  JSON input contains illegal start.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test101_illegalstart() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    assertThrows(GeneralParserException.class, () -> {
      String tststr = "}\"f1\":\"v1\", \"f2\":-1234.3234, \"f3\":true, \"f4\":false, \"f5\":null, \"f6\":[1234, true]}";
      jp.parse(new StringReader(tststr));
    });
  }

  /**
   * <p>Test 102.  JSON input has no field separator.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test102_nosep() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    assertThrows(GeneralParserException.class, () -> {
      String tststr = "{\"f1\":\"v1\", \"f2\":-1234.3234, \"f3\":true \"f4\":false, \"f5\":null, \"f6\":[1234, true]}";
      jp.parse(new StringReader(tststr));
    });
  }

  /**
   * <p>Test 103.  JSON input field is not a string.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test103_nofieldnamestr() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    assertThrows(GeneralParserException.class, () -> {
      String tststr = "{\"f1\":\"v1\", \"f2\":-1234.3234, f3:true, \"f4\":false, \"f5\":null, \"f6\":[1234, true]}";
      jp.parse(new StringReader(tststr));
    });
  }

  /**
   * <p>Test 104.  JSON input field separator.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test104_nofieldsep() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    assertThrows(GeneralParserException.class, () -> {
      String tststr = "{\"f1\":\"v1\", \"f2\":-1234.3234, \"f3\"true, \"f4\":false, \"f5\":null, \"f6\":[1234, true]}";
      jp.parse(new StringReader(tststr));
    });
  }

  /**
   * <p>Test 105.  JSON input field value.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test105_nofieldval() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    assertThrows(GeneralParserException.class, () -> {
      String tststr = "{\"f1\":\"v1\", \"f2\":-1234.3234, \"f3\":true, \"f4\":fwe23r2r23fewf, \"f5\":null, \"f6\":[1234, true]}";
      jp.parse(new StringReader(tststr));
    });
  }

  /**
   * <p>Test 106.  JSON object end.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test106_arrnoval() throws IOException, GeneralParserException
  {
    JSONDocumentParser jp = new JSONDocumentParser();

    assertThrows(GeneralParserException.class, () -> {
      String tststr = "[1234, \"fdsfdsa\", true, false, 232ior32]";
      jp.parse(new StringReader(tststr));
    });
  }
}
