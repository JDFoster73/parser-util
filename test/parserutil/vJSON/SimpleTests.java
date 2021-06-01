package test.parserutil.vJSON;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.Test;

import parserutil.impl.json.JSONField;
import parserutil.impl.json.JSONObjectImpl;
import parserutil.impl.json.JSONValueHolder;
import parserutil.impl.json.parser.JSONParser;
import parserutil.main.GeneralParserException;

public class SimpleTests
{
  
  @Test
  public void test1() throws IOException
  {
    JSONParser jp = new JSONParser();
    InputStream resourceAsStream = getClass().getModule().getResourceAsStream("test/vJSON/test1.json");
    try
    {
      JSONValueHolder parse = jp.parse(new InputStreamReader(resourceAsStream));
      
      System.out.println(parse);
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (GeneralParserException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } // Empty object
    
  }
  
  @Test
  public void test2() throws IOException
  {
    JSONParser jp = new JSONParser();
    InputStream resourceAsStream = getClass().getModule().getResourceAsStream("test/vJSON/test2.json");
    try
    {
      JSONValueHolder parse = jp.parse(new InputStreamReader(resourceAsStream));
      
      System.out.println(parse.toString());
      
      //The root is a JSON object.
      JSONObjectImpl objectType = parse.getObjectType();
      
      //Get the field name (should be without quotes) and the field value (also without quotes).
      JSONField fld1 = objectType.getField(0);
      String fldName = fld1.getFieldName();
      String fldVal = fld1.getFieldValue().getValueType().getValue();
      
      assertTrue(fldName.equals("field1"));
      assertTrue(fldVal.equals("the field value"));
      
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (GeneralParserException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } // Empty object
    
  }
}
