
package com.acn.groovy.assessment
@Grab(group='org.apache.groovy', module='groovy-ant', version='5.0.1')
@Grab(group='org.apache.ant', module='ant', version='1.10.14')
@Grab(group='org.apache.ant', module='ant-launcher', version='1.10.14')

import java.text.SimpleDateFormat
class SearchAndReplace {
	/**
	 * I used DirectoryScanner instead of straightforward, algorithmic approach or AntBuilder.replaceregexp. I do have researched about depth-first and breadth-first algo approach,
	 * but using DirectoryScanner is much readable and reusable, while replaceregexp will increase complexity of the code as such.
	 *
	 * this function handles only ".txt" file for search and replace
	 *
	 * @param rootDirectory is the directory to search
	 * @param searchTextPattern is the text or regex pattern to search
	 * @param replaceWith is for replacing text
	 * @param regex if its true, then use regex replacement
	 * @param fileExtension use to filter pattern
	 * @param logFile is to log modified files
	 * @param backupExtension is for the  backup extension (".bak")
	 */


	protected static void replaceInDirectory(File rootDirectory,
			String searchTextPattern,
			String replaceWith,
			boolean regex,
			String fileExtension,
			File logFile,
			String backupExtension) {

		if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
			throw new IllegalArgumentException("The provided path is not a directory or does not exists: ${rootDirectory}")
		}

		println "Replacing files...."
		println "Start Time: ${new Date()}"
		println "Directory: ${rootDirectory}"

		def modifiedFiles = []

		try {

			def scanner = new org.apache.tools.ant.DirectoryScanner()
			scanner.setBasedir(rootDirectory)

			// fileExtension can be like "*.txt", "**/*.groovy", etc.
			scanner.setIncludes([fileExtension] as String[])

			scanner.scan()
			def files = scanner.getIncludedFiles()

			files.each { relativePath ->

				File file = new File(rootDirectory, relativePath)

				// create the backup file
				if (backupExtension) {
					File backupFile = new File(file.parentFile, file.name + backupExtension)
					backupFile.bytes = file.bytes
				}

				// replace files
				if (regex) {
					file.text = file.text.replaceAll(searchTextPattern, replaceWith)
				} else {
					file.text = file.text.replace(searchTextPattern, replaceWith)
				}

				modifiedFiles << file.absolutePath
			}

			// Logging via console print
			if (logFile) {
				if (!logFile.parentFile.exists()) {
					boolean created = logFile.parentFile.mkdirs()
					println "Created parent dirs: ${created}"
				}
				logFile.withWriterAppend { writer ->
					writer.writeLine("Replaced Files at ${new Date()}")
					modifiedFiles.each { writer.writeLine(it) }
					writer.writeLine("Total modified files: ${modifiedFiles.size()}")
					writer.writeLine("--------------------")
				}
			}



			println "Replacing files complete."
			println "Modified files (${modifiedFiles.size()}):"
			modifiedFiles.each { println it }
		} catch(Exception e) {
			println "Error: ${e.message}"
			e.printStackTrace()
		}

		println "End Time: ${new Date()}"
		println "------- Process Finished ------"
	}

	// a override method to address error for groovy
	protected static void replaceInDirectory(File rootDirectory,
			String searchTextPattern,
			String replaceText,
			boolean regex,
			String fileExtension) {
		replaceInDirectory(rootDirectory, searchTextPattern, replaceText, regex, fileExtension, null, ".bak")
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			println """
            Usage: groovy MainGroovy.groovy <directory> <searchTextPattern> <replaceText> [--regex] [--fileExtension=*.txt] [--logFile=/path/to/log.txt]
            
            <directory>    : required, Directory containing text files
            <searchTextPattern>   : required, Text or pattern to search
            <replaceText>  : required, Text to replace with
            --regex        : flag to treat searchTextPattern as regex
            --fileExtension  :  defaults to '*.txt'
            --logFile      :  path to log file
            """
			System.exit(1)
		}

		File directory = new File(args[0])
		String searchText = args[1]
		String replaceText = args[2]


		boolean regex = false
		String fileExtension = "*.txt"
		File logFile = null


		args.each { arg ->
			if (arg == "--regex") {
				regex = true
			} else if (arg.startsWith("--fileExtension=")) {
				fileExtension = arg.split("=", 2)[1]
			} else if (arg.startsWith("--logFile=")) {
				logFile = new File(arg.split("=", 2)[1])
			}
		}

		// Run the search and replace method
		try {
			SearchAndReplace.replaceInDirectory(directory, searchText, replaceText, regex, fileExtension, logFile, ".bak")
		} catch (Exception e) {
			println "An error occured: ${e.message}"
			e.printStackTrace()
			System.exit(1)
		}
	}
}

