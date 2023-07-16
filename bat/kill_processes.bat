@echo off
setlocal

set port=9080

echo Searching for process on port %port%...

REM Find the PID (Process ID) using netstat and filter by port
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%port%') do (
    set "pid=%%a"
)

REM If PID is found, kill the process
if defined pid (
    echo Killing process with PID %pid%...
    taskkill /F /PID %pid%
) else (
    echo No process found on port %port%.
)

set port=8080

REM Find the PID (Process ID) using netstat and filter by port
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%port%') do (
    set "pid=%%a"
)

REM If PID is found, kill the process
if defined pid (
    echo Killing process with PID %pid%...
    taskkill /F /PID %pid%
) else (
    echo No process found on port %port%.
)

endlocal
