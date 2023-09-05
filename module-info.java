
module tools.parseutil
{
  exports parserutil.impl.csv;
  exports parserutil.impl.csv.parser;
  exports parserutil.impl.json;
  exports parserutil.impl.json.parser;
  exports parserutil.impl.textreplace;
  exports parserutil.impl.textreplace.parser;
  exports parserutil.main;
  requires static junit;
}