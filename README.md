# Android Source Scout
Android Security Static Analysis Tool for Android Studio performs static analysis of Android source code to detect security vulnerabilities. Detecting security vulnerabilities as they are written greatly decreases the cost to fix them. Additionally, educating developers about the safe way to write code has many additional benefits. 

# Status
Currently detects the following issues:

* Intent Implicitly Exported by TargetSDKVersion
* targetSdkVersion is not latest
* Explicitly Exported
* Implicitly Exported Intents
* ManifestBackupsInspection
* minSDKVersion Not Set
* maxSdkVersion is set
* Debuggable
* Task Manager Snooping
* TapJacking
* Disable Certificate Validation
* Sensitive Fields Not Protected
* textPassword needs set
* SQL Injection - Simple
* SQL Injection - Advanced
* Path Traversal
* Broadcast Usage
* Clipboard Usage
* External Storage
* Geolocation
* World Writeable Output
* WebView JavaScript Enabled
* XXE JAXP
* XXE SAX
* Insecure HTTP Communications
* Insecure WebSocket Communications

# Accuracy
Some additional security mechanisms have been introduced in Android since this was written, so it is important to keep up with these changes to eliminate all false positives. Additionally, the checks are not perfect, so it is important to use this as a helpful tool, but not rely too heavily on it for security.

# Installation
SourceScout installs as a plugin for Android Studio.

## Install Plugin
Within Android Studio, choose `File`->`Settings`->`Plugins`, then select Select `Install Plugin from Disk` and select the downloaded jar.

## Begin Finding Vulnerabilities
The setup is now complete. You will likely see the highest density of issues in the AndroidManifest.xml. You can also view a list of all identified issues by choosing Analyze->Inspect Code… and the issues will be listed under Android Security Issues.

# Screenshots

![ExternalStorage](/screenshots/externalstorage.png?raw=true "externalstorage")

![Clipboard](/screenshots/clipboard.png?raw=true "Clipboard")

![intentfilter](/screenshots/intentfilter.png?raw=true "intentfilter")

![xxe](/screenshots/xxe.png?raw=true "xxe")
