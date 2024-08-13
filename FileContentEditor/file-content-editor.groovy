import groovy.io.FileType

class FileContentEditor {
	static List<String> modifiedFiles = []

	// This method is used to backup files
	static void processBackup(File targetPath, File backupPath) {
		// Check if backup path exists. Create if not exists
		if (!backupPath.exists()) {
			backupPath.mkdirs()
		}

		targetPath.eachFileRecurse(FileType.FILES) {
			file -> {
				def relativePath = file.absolutePath - targetPath.absolutePath
				def backupFilePath = new File(backupPath, relativePath)
				backupFilePath.parentFile.mkdirs()
				file.withInputStream {
					input -> backupFilePath.withOutputStream {
						output -> output << input
					}
				}
			}
		}

		println "Successfully backed up files"
	}

	// This method is used to search file contents and replace a text
	static void processSearchAndReplace(File targetPath, String searchText, String replaceText) {
		targetPath.eachFileRecurse(FileType.FILES) {
			file -> {
				def fileContent = file.text
				if (fileContent.contains(searchText)) {
					def newFileContent = fileContent.replace(searchText, replaceText)
					file.write(newFileContent)
					modifiedFiles.add(file.path)
					println "Successfully modified ${file.path}"
				}
			}
		}
	}

	// This method is used to generate log
	static void processLogging(File logPath) {
		// Check if log path exists. Create if not exists
		if (!logPath.parentFile.exists()) {
			logPath.parentFile.mkdirs()
		}

		logPath.withWriter {
			writer -> modifiedFiles.each {
				filePath -> writer.writeLine("Modified: ${filePath}")
			}
		}

		println "Successfully logged"
	}

	// Main script execution
	// This script is used to search text from the file contents in a given directory
	static void main(String[] args) {
		if (args.length < 4) {
			println "Missing required arguments: <Target Directory> <Backup Directory> <Search Text> <Replace Text>"
		}

		def targetDirectory = args[0]
		def backupDirectory = args[1]
		def searchText      = args[2]
		def replaceText     = args[3]
		def logDirectory    = args.length > 4 ? args[4] : targetDirectory

		def targetPath = new File(targetDirectory)
		def backupPath = new File(backupDirectory)
		def logPath    = new File(logDirectory + "/FileContentEditor.log")

		// Check if target path exists and is a directory
		if (targetPath.exists() && targetPath.isDirectory()) {
			// Execute file backup
			processBackup(targetPath, backupPath)

			// Execute file replacement
			processSearchAndReplace(targetPath, searchText, replaceText)

			// Execute logging
			processLogging(logPath)
		} else {
			println "Invalid file directory"
		}
	}
}
