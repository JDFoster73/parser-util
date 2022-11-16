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
package test.parserutil.csv;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import org.junit.Test;

import parserutil.impl.csv.parser.CSVParser;
import parserutil.main.GeneralParserException;

/**
 * <p>JSON document parser function tests.  Make sure it correctly parses well-behaved input and
 * rejects ill-formed input.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class CSVElementFunctionTests
{
  /**
   * <p>Test 1.  JSON elements check.
   * 
   * @throws IOException
   * @throws GeneralParserException
   */
  @Test
  public void test1_check_eles() throws IOException, GeneralParserException
  {
    String tststr = "{\"f1\":\"v1\", \"f2\":-1234.3234, \"f3\":true, \"f4\":false, \"f5\":null, \"f6\":[1234, true]}";
    
    Reader rd = new FileReader("D:\\Files\\Work\\Operations\\Activity\\2022\\ail\\41\\generated\\telegram traces/cran41all.csv", Charset.forName("UTF-8"));
    
    CSVParser jp = new CSVParser();

    //Value should be a single string.
    jp.parse(rd, (t) -> {
      if(t.getValue().endsWith("\n")) 
      {
        System.out.println("newline!!");
      }
      System.out.println(t);
    });
  }
}
