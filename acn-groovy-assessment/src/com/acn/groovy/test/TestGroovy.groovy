package com.acn.groovy.test


import com.acn.groovy.assessment.SearchAndReplace

// created a test class for your convenience
// it creates the "first.txt" and "second.txt" file first with the stated string, and then replaces it.
// you can try to change the name of the text file without deleting the previous one to fully test it

File root = new File("acn-groovy-test")
root.mkdirs()

new File(root, "first.txt").text = "Hello ACN"
new File(root, "second.txt").text = "Hello Again ACN"

println "Before:"
println new File(root, "first.txt").text
println new File(root, "second.txt").text

// pass file filter or regex flag
SearchAndReplace.replaceInDirectory(root, "Hello", "Hi", false, "**/*.txt")

// check backup files, which have the ".bak" extension
println "\nBackup files:"
root.eachFileMatch(~/.*\.bak/) { println it.name }

// after replacement
println "\nAfter:"
println new File(root, "first.txt").text
println new File(root, "second.txt").text

