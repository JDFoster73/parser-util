package parserutil.impl.json.parser;

import java.util.ArrayList;
import java.util.ResourceBundle;

import parserutil.main.GeneralParserStateMachine;

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
 * 
 * <p>This provides state checking of JSON input.  It makes sure that each token is valid following the
 * previous token and that the input is therefore valid and correct.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONParseStateMachine<T> implements GeneralParserStateMachine<JSONTokenDescriptor>
{
  enum STATE
  {
    START,
    FIELD_NAME,
    FIELD_ASG,    //Field assignment char ':'
    FIELD_SEP,    //Field separator char ',' or end '}'
    FIELD_DES,    //Field type decision - identifier (string, literal, number, boolean), object or array.
    FIELD_VAL,
    FIELD_OBJ,
    FIELD_ARRAY,
    FIELD_END,
    END           //No further tokens allowed.
  }
    
  /**
   * <p>Nesting list.  As this instance is called as tokens are processed, it must have a means of maintaining the token tree state.
   */
  private final ArrayList<GeneralParserStateMachine<JSONTokenDescriptor>> nestingList = new ArrayList<>();
  
  /**
   * <p>Create instance.
   */
  public JSONParseStateMachine()
  {
    reset();
  }
  
  /**
   * <p>Check the token.  A non-"" return value indicates an error.
   */
  public String check(JSONTokenDescriptor desc)
  {
    //Check the input is not null.
    if(desc == null) throw new NullPointerException();
    
    //Comment - don't check, don't move state.
    if(desc.getType() == JSONTokenType.COMMENT) return "";

    //The currently processed JSON instance checker is the last entry in the nesting list. 
    return nestingList.get(nestingList.size() - 1).check(desc);
  }

  /**
   * <p>Set the current state back to start.
   */
  public void reset()
  {
    //Add a start validator to the nesting list.
    nestingList.add(new StartValidator());
  }

  /**
   * <p>The section (object, array contents for example) has finished processing.  Remove it from the nesting list.
   */
  private void completeSection()
  {
    //Remove the last entry from the nesting list.
    nestingList.remove(nestingList.size() - 1);
  }
  
  /**
   * <p>Add a new section (object, array contents for example) to the nesting list.
   * 
   * @param val
   */
  private void addSection(GeneralParserStateMachine<JSONTokenDescriptor> val)
  {
    //Remove the last entry from the nesting list.
    nestingList.add(val);
  }
  
  /**
   * <p>Validate the initial token.  JSON input can start with a value, an object start '{' or an array start '['.
   * 
   * @author jdf19
   *
   */
  private class StartValidator implements GeneralParserStateMachine<JSONTokenDescriptor>
  {
    
    @Override
    public String check(JSONTokenDescriptor desc)
    {
      //Field type decision.
      if(desc.getDesignation() == JSONTokenDesignation.OP_START_OBJ)
      {
        nestingList.add(new ObjectValidator());
        return "";
      }
      else if(desc.getDesignation() == JSONTokenDesignation.OP_START_ARR)
      {
        nestingList.add(new ArrayValidator());
        return "";
      }
      else if(desc.getDesignation() != JSONTokenDesignation.ID_STR)
      {
        nestingList.add(new StringValidator());
        return "";
      }
      //None of the required tokens found.
      return ResourceBundle.getBundle("json.parser").getString("nostartok");
    }
    
  }

  /**
   * <p>Validate a token while processing a JSON object.
   * 
   * @author jdf19
   *
   */
  private class ObjectValidator implements GeneralParserStateMachine<JSONTokenDescriptor>
  {
    //Start - need to get the field identifier first.
    private STATE state = STATE.FIELD_NAME;
    
    /**
     * <p>Check the next token against the current parsing state.  Only certain tokens are allowed to follow other tokens in a JSON object.
     */
    @Override
    public String check(JSONTokenDescriptor tokenDescriptor)
    {
      switch(state)
      {
        case FIELD_NAME:
          //We're looking for an identifier.
          //Check we've got a string.
          //Check for end of array.  This would be the case in the event of an empty object.
          if(tokenDescriptor.getDesignation() == JSONTokenDesignation.OP_FINISH_OBJ)
          {
            //Finished object def.  Complete this section.
            completeSection();
            return "";
          }
          //Not the end of object and not a string.  Object fields must start with a string identifier.
          if(tokenDescriptor.getDesignation() != JSONTokenDesignation.ID_STR) return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("objfldnostr");
          //Still here - we now need the field assignment character ':'.
          state = STATE.FIELD_ASG;
          //Success.
          return "";
        case FIELD_ASG:
          //Separator needed.  After this we need to get a value.
          if(tokenDescriptor.getDesignation() != JSONTokenDesignation.OP_FLDASG) return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("objfldnosep");
          //Success.  Got the field name and separator, now get the field value.
          state = STATE.FIELD_DES;
          return "";
        case FIELD_DES:
          //We're looking for an OBJECT, ARRAY OR VALUE.
          if(tokenDescriptor.getDesignation() == JSONTokenDesignation.OP_START_OBJ)
          {
            state = STATE.FIELD_SEP;             //We're looking for field separator (or end) when nested validator completes.
            addSection(new ObjectValidator());
            return "";
          }
          else if(tokenDescriptor.getDesignation() == JSONTokenDesignation.OP_START_ARR)
          {
            state = STATE.FIELD_SEP;             //We're looking for field separator (or end) when nested validator completes.
            addSection(new ArrayValidator());
            return "";
          }
          else if(tokenDescriptor.getType() == JSONTokenType.IDENTIFIER)
          {
            state = STATE.FIELD_SEP;    //Looking for a simple value.
            return "";
          }
          //None of the required tokens found.
          return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("objnoval");          
        case FIELD_SEP:
          //We're looking for separator or end.
          if(tokenDescriptor.getDesignation() == JSONTokenDesignation.OP_SEP)
          {
            //Expecting another field.
            state = STATE.FIELD_NAME;
            return "";
          }
          else if(tokenDescriptor.getDesignation() == JSONTokenDesignation.OP_FINISH_OBJ)
          {
            //Finished object def.  Complete this section.
            completeSection();
            return "";
          }
          else
          {
            return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("objfol");
          }
        default:
          break;
      }
      
      //Default - illegal state. 
      return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("badstate");
    }
  }
  
  /**
   * <p>Validate a token while processing a JSON array.
   * 
   * @author jdf19
   *
   */
  private class ArrayValidator implements GeneralParserStateMachine<JSONTokenDescriptor>
  {
    //Start - need to decide on the field type first.
    private STATE state = STATE.FIELD_DES;

    @Override
    public String check(JSONTokenDescriptor desc)
    {
      switch(state)
      {
        case FIELD_DES:
          //We're looking for a VALUE.
          if(desc.getDesignation() == JSONTokenDesignation.OP_START_OBJ)
          {
            state = STATE.FIELD_SEP;             //We're looking for field separator (or end) when nested validator completes.
            addSection(new ObjectValidator());
            return "";
          }
          else if(desc.getDesignation() == JSONTokenDesignation.OP_START_ARR)
          {
            state = STATE.FIELD_SEP;             //We're looking for field separator (or end) when nested validator completes.
            addSection(new ArrayValidator());
            return "";
          }
          else if(desc.getDesignation() == JSONTokenDesignation.OP_FINISH_ARR)
          {
            //Finished array def; empty.  Complete this section.
            completeSection();
            return "";
          }
          else if(desc.getType() == JSONTokenType.IDENTIFIER)
          {
            state = STATE.FIELD_SEP;    //Simple value - looking for separator or end.
            return "";
          }
          //None of the required tokens found.   
          return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("arrnoval");          
        case FIELD_SEP:
          //We're looking for separator or end.
          if(desc.getDesignation() == JSONTokenDesignation.OP_SEP)
          {
            //Expecting another field.
            state = STATE.FIELD_DES;
            return "";
          }
          else if(desc.getDesignation() == JSONTokenDesignation.OP_FINISH_ARR)
          {
            //Finished object def.  Complete this section.
            completeSection();
            return "";
          }
          else
          {
            return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("arrfol");
          }
        default:
          break;
      }

      //Default - illegal state. 
      return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("badstate");
    }
    
  }

  /**
   * <p>Validate a token while processing a JSON string.
   * 
   * @author jdf19
   *
   */
  private class StringValidator implements GeneralParserStateMachine<JSONTokenDescriptor>
  {
    @Override
    public String check(JSONTokenDescriptor t)
    {
      //No tokens following the string are permitted.  
      return ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("valfol");
    }
    
  }
}
