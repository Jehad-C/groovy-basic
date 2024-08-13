FileContentModifier Script Instruction

Description:
This script is used to recursively search for and replace specified text within files in a given directory

Prerequisites:
Ensure you have Groovy installed

Usage:
groovy <Name-of-the-file> <Target-Directory> <Backup-Directory> <Search-Text> <Replace-Text> [<Log-Directory>]

Example usage
groovy file-content-editor.groovy ./TargetFolder ./BackupFolder IAmSearchText IAmReplaceText

groovy file-content-editor.groovy ./TargetFolder ./BackupFolder IAmSearchText IAmReplaceText ./Logs

Parameters:
<Target-Directory> - The directory where the script will search for file contents
<Backup-Directory> - The directory where the backup of the original files will be saved
<Search-Text>      - The text you want to search within the file contents
<Replace-Text>     - The text that will replace the <Search-Text>
[<Log-Directory>]  - (Optional) The directory where logs will be saved. Default to <Target-Directory>

