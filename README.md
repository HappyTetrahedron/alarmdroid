# Alarmdroid

This very basic Android app adds an account type to your settings. You can specify an HTTP URL, and AlarmDroid will occasionally POST your next alarm time there.

The request body is a JSON object which is either empty (no alarm set) or contains a single key `next_alarm` containing the timestamp of the next alarm in epoch milliseconds.

You'll have to compile this app yourself and add a root certificate for whichever service you're making the request to, since Android only allows secure HTTPS communication.
Put the certificate in `app/src/main/res/raw/alarmdroid_ca`.
