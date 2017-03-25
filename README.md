# Alarmdroid

This very basic Android app adds an account type to your settings. You can specify an address and port, and AlarmDroid will occasionally send your next alarm time there.

The time is sent as an UTF-8 encoded string, which is either "none" (if no alarm is set) or a timestamp in **milliseconds** since epoch time.
