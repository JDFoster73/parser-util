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
package parserutil.impl.csv.parser;

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
public class CSVParseStateMachine<T> implements GeneralParserStateMachine<CSVTokenDescriptor>
{     
  private CSVTokenDescriptor lastToken;
  
  /**
   * <p>Create instance.
   */
  public CSVParseStateMachine()
  {
    reset();
  }
  
  /**
   * <p>Check the token.  If the state machine detects an error with the input state then it will throw a {@link GeneralParserException}.
   * @throws GeneralParserException 
   */
  public void check(CSVTokenDescriptor desc) throws GeneralParserStateMachineException
  {
    //Check the input is not null.
    if(desc == null) throw new NullPointerException();

    //In theory any progression of tokens is acceptable??
    
//    //If last token not null then check the progression.  If last token was null then in theory
//    //we can have an empty token or an empty line.
//    if(lastToken != null)
//    {
//      switch(lastToken.getTokenType())
//      {
//        case FIELD_DELIM:
//          //
//        case ROW_DELIM:
//        case VALUE:
//      }
//    }
    
    //Set last token.
    lastToken = desc;
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
    lastToken = null;
  } 
}
