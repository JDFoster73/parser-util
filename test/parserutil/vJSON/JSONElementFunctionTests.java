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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import parserutil.impl.json.parser.JSONElementParser;
import parserutil.impl.json.parser.JSONTokenDesignation;
import parserutil.main.GeneralParserException;

/**
 * <p>JSON document parser function tests.  Make sure it correctly parses well-behaved input and
 * rejects ill-formed input.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONElementFunctionTests
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
    JSONElementParser jp = new JSONElementParser();

    List<JSONTokenDesignation> desList = new ArrayList<>();
    desList.add(JSONTokenDesignation.OP_START_OBJ);
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_FLDASG);
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_SEP);
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_FLDASG);
    desList.add(JSONTokenDesignation.ID_NUM_REA);
    desList.add(JSONTokenDesignation.OP_SEP);
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_FLDASG);
    desList.add(JSONTokenDesignation.ID_BOOL);
    desList.add(JSONTokenDesignation.OP_SEP);
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_FLDASG);
    desList.add(JSONTokenDesignation.ID_BOOL);
    desList.add(JSONTokenDesignation.OP_SEP);
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_FLDASG);
    desList.add(JSONTokenDesignation.ID_NULL);
    desList.add(JSONTokenDesignation.OP_SEP);

    //Array.
    desList.add(JSONTokenDesignation.ID_STR);
    desList.add(JSONTokenDesignation.OP_FLDASG);
    desList.add(JSONTokenDesignation.OP_START_ARR);
    desList.add(JSONTokenDesignation.ID_NUM_INT);
    desList.add(JSONTokenDesignation.OP_SEP);
    desList.add(JSONTokenDesignation.ID_BOOL);
    desList.add(JSONTokenDesignation.OP_FINISH_ARR);

    desList.add(JSONTokenDesignation.OP_FINISH_OBJ);

    //Value should be a single string.
    jp.parse(new StringReader(tststr), (e) -> {
      JSONTokenDesignation remove = desList.remove(0);
      JSONTokenDesignation designation = e.descriptor.getDesignation();
      assertTrue(designation.equals(remove));
    });
  }
}
