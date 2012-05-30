Dim oShell
Set oShell = WScript.CreateObject ("WScript.Shell")
oShell.run "java -jar ParseChop.jar", 0, False
Set oShell = Nothing


