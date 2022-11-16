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
package parserutil.impl.javasrc.parser;

import java.util.ArrayList;

import parserutil.main.GeneralParserException;
import parserutil.main.GeneralParserStateMachine;
import parserutil.main.GeneralParserStateMachineException;

/**
 * <p>This provides state checking of JSON input.  It makes sure that each token is valid following the
 * previous token and that the input is therefore valid and correct.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JavaSourceParseStateMachine<T> implements GeneralParserStateMachine<JavaSourceTokenDescriptor>
{
  enum STATE
  {
    START,
    SPEC_PACKAGE,       //Package specifier.
  }
    
  /**
   * <p>Nesting list.  As this instance is called as tokens are processed, it must have a means of maintaining the token tree state.
   */
  private final ArrayList<GeneralParserStateMachine<JavaSourceTokenDescriptor>> nestingList = new ArrayList<>();
  
  /**
   * <p>Create instance.
   */
  public JavaSourceParseStateMachine()
  {
    reset();
  }
  
  /**
   * <p>Check the token.  If the state machine detects an error with the input state then it will throw a {@link GeneralParserException}.
   * @throws GeneralParserException 
   */
  public void check(JavaSourceTokenDescriptor desc) throws GeneralParserStateMachineException
  {
    //Check the input is not null.
    if(desc == null) throw new NullPointerException();
    
    //Comment - don't check, don't move state.
    if(desc.getType() == JavaSourceTokenType.COMMENT) return;

//    //The currently processed JSON instance checker is the last entry in the nesting list. 
//    nestingList.get(nestingList.size() - 1).check(desc);

  }

  /**
   * <p>Initialise the state of the state machine.
   */
  @Override
  public void initialise()
  {
    //
    reset();
  }

  /**
   * <p>Set the current state back to start.
   */
  public void reset()
  {
    //Clear the nesting list.
    nestingList.clear();
    
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
  private void addSection(GeneralParserStateMachine<JavaSourceTokenDescriptor> val)
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
  private class StartValidator implements GeneralParserStateMachine<JavaSourceTokenDescriptor>
  {
    
    @Override
    public void check(JavaSourceTokenDescriptor desc) throws GeneralParserStateMachineException
    {
      //None of the required tokens found.
      //throw new GeneralParserStateMachineException(ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("nostartok"));
    }

    @Override
    public void initialise()
    {
      //No need to initialise this instance.  The main parser instance will discard it if it is reset.      
    }
    
  }

}
