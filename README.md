# ACN Groovy Assessment

To test the code or its functionality, you may refer to these steps:

1. Create a ".txt" file in the same repository of the groovy file (.\acn-groovy-assessment\src\com\acn\groovy\assessment)
2. Add a sample text to test for replacement
3. create a "log.txt" file for logging
4. Run this command, and replace the substitute value for the argument parameter
```bash
MainGroovy.groovy .\acn-groovy-assessment\src\com\acn\groovy\assessment "oldText" "newText" --regex --fileExtension="*.txt" --logFile="/tmp/log.txt"
```

`MainGroovy.groovy` is the groovy file or script we wish to run. You might also need to add your home directory and project directory when running (e.g. C://Users/com/acn/groovy/assessment/MainGroovy.groovy)

`.\acn-groovy-assessment\src\com\acn\groovy\assessment` is the full directory that the same ".txt" file are present and the groovy file (you might need to add the full home directory here)

`oldText` is the sample text we wish to replace

`newText` is the replacement string/text

`--regex` is a boolean flag to treat

`--fileExtension` the file extension of the file to search and replace

`--logFile` the directory of the "log.txt" file



I also accidentally added the test class I'm using as a test/debug. It might not run on your terminal/machine, but in an IDE might.
