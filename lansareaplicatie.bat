@echo off
Z:
cd \
ECHO WScript.CreateObject("WScript.Shell").Run "java -jar ""Z:\arhiva.jar""", 0, false > temp_run.vbs
temp_run.vbs
DEL temp_run.vbs
exit /B
