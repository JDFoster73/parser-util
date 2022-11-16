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
package test.parserutil.javasource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.junit.Test;

import parserutil.impl.javasrc.JavaSrcFile;
import parserutil.impl.javasrc.parser.JavaSourceBaseParser;
import parserutil.main.GeneralParserException;

/**
 * <p>
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JavaSourceFunctionTests
{
  /**
   * <p>Test 1.  Very simple text replacement test.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test1_strvalue() throws IOException, GeneralParserException
  {
    System.out.println(Character.isJavaIdentifierPart('_'));
    
    JavaSourceBaseParser jsbp = new JavaSourceBaseParser();
    
    //Reader rdr = new StringReader("/* this is a comment */ //So is this\n");
    Reader rdr = new FileReader("d:/temp/TDoubleByteHash.java");
    JavaSrcFile sourceFile = jsbp.getSourceFile(rdr);
    
    System.out.println(sourceFile);
  }
}
