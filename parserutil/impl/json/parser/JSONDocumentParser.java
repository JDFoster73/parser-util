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
package parserutil.impl.json.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.ResourceBundle;

import parserutil.impl.json.JSONArrayImpl;
import parserutil.impl.json.JSONField;
import parserutil.impl.json.JSONObjectImpl;
import parserutil.impl.json.JSONValueHolder;
import parserutil.impl.json.JSONValueImpl;
import parserutil.main.GeneralParser;
import parserutil.main.GeneralParserException;
import parserutil.main.GeneralParserToken;

/**
 * <p>
 * The {@link JSONDocumentParser} extends the functionality of the
 * {@link GeneralParser} for Unit Configuration Format files. This format is
 * similar to JSON and provides a simple way of including field name:field value
 * entries.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONDocumentParser extends JSONAbstractParser
{
  /**
   * <p>
   * Parse the given content.
   * 
   * @param content
   * @throws IOException
   * @throws GeneralParserException
   */
  public JSONValueHolder parse(Reader content) throws IOException, GeneralParserException
  {
    try
    {
      //Initialise the parser.
      initParser();
      
      // Parse the content.
//      parser.init(new JSONParseStateMachine<>());
      
      // Get the first non-comment token.
      GeneralParserToken<JSONTokenDescriptor> nextToken = getNextJSONToken(content);
      
      // Check the next token. Null implies the end of stream.
      if (nextToken != null)
      {
        // Check status.
        if (!"".equals(nextToken.machineStatus))
          throw new GeneralParserException(nextToken.machineStatus, nextToken.getLocation());
        
        // Check type. Top level type can be an object, array or a string.
        if (nextToken.descriptor.getDesignation() == JSONTokenDesignation.OP_START_OBJ)
        {
          return doJSONParseObject(content);
        }
        else if (nextToken.descriptor.getDesignation() == JSONTokenDesignation.OP_START_ARR)
        {
          return doJSONParseArray(content);
        }
        //Test for a simple value
        else if (nextToken.descriptor.getType() == JSONTokenType.IDENTIFIER)
        {
          // Return simple value.
          return new JSONValueHolder(new JSONValueImpl(nextToken));
        }
        else
        {
          //No start token type.
          throw new GeneralParserException(ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("general"), null);
        }
      }
    }
    catch (NullPointerException npe)
    {
      throw new GeneralParserException(ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("general"), null, npe);
    }
    
    // We can't be here in normal operation - throw an illegal state exception.
    throw new IllegalStateException();
  }
  
  /**
   * <p>
   * Parse the given object contents of the parent object.
   * 
   * @param parent
   * @param content
   * @throws IOException
   * @throws GeneralParserException
   */
  private JSONValueHolder doJSONParseObject(Reader content) throws IOException, GeneralParserException
  {
    // Create a return value.
    JSONObjectImpl ret = new JSONObjectImpl();
    JSONValueHolder r = new JSONValueHolder(ret);
    
    // Pull fields until object finished.
    boolean pullFinished = false;
    while (!pullFinished)
    {
      // Get identifier.
      GeneralParserToken<JSONTokenDescriptor> idToken = getNextJSONToken(content);
      
      // Check for empty object.
      if (idToken.descriptor.getDesignation() == JSONTokenDesignation.OP_FINISH_OBJ)
      {
        pullFinished = true;
        continue;
      }
      
      String identifier = idToken.tokenValue;
      // Ignore separator.
      getNextJSONToken(content);// parser.getNextToken(content);
      // Get value.
      GeneralParserToken<JSONTokenDescriptor> valueToken = getNextJSONToken(content);
      // Determine obj, array or value.
      if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_START_OBJ)
      {
        ret.addField(new JSONField(identifier, doJSONParseObject(content)));
      }
      else if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_START_ARR)
      {
        ret.addField(new JSONField(identifier, doJSONParseArray(content)));
      }
      else if (valueToken.descriptor.getType() == JSONTokenType.IDENTIFIER)
      {
        ret.addField(new JSONField(identifier, new JSONValueHolder(new JSONValueImpl(valueToken))));
      }
      
      // Get next. If it's a close brace then we're done.
      valueToken = getNextJSONToken(content);
      if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_FINISH_OBJ)
        pullFinished = true;
    }
    
    // Return.
    return r;
  }
  
  /**
   * <p>
   * Parse array contents of the given array instance.
   * 
   * @param parent
   * @param content
   * @throws IOException
   * @throws GeneralParserException
   */
  private JSONValueHolder doJSONParseArray(Reader content) throws IOException, GeneralParserException
  {
    // Create a return value.
    JSONArrayImpl ret = new JSONArrayImpl();
    JSONValueHolder r = new JSONValueHolder(ret);
    
    // Pull fields until object finished.
    boolean pullFinished = false;
    while (!pullFinished)
    {
      // Get value.
      GeneralParserToken<JSONTokenDescriptor> valueToken = getNextJSONToken(content);
      
      // Check for empty array.
      if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_FINISH_ARR)
      {
        pullFinished = true;
        continue;
      }
      
      // Determine obj, array or value.
      if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_START_OBJ)
      {
        ret.addField(doJSONParseObject(content));
      }
      else if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_START_ARR)
      {
        ret.addField(doJSONParseArray(content));
      }
      else if (valueToken.descriptor.getType() == JSONTokenType.IDENTIFIER)
      {
        ret.addField(new JSONValueHolder(new JSONValueImpl(valueToken)));
      }
      
      // Get next. If it's a close brace then we're done.
      valueToken = getNextJSONToken(content);
      if (valueToken.descriptor.getDesignation() == JSONTokenDesignation.OP_FINISH_ARR)
        pullFinished = true;
    }
    
    // Return.
    return r;
  }
  
}
