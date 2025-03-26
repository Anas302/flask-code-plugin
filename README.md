# Flask Code Analysis PyCharm Plugin
PyCharm plugin that conducts Flask-specific code analysis in Python.
As of now, the plugin does the following:
 * Detects name mismatch between routes parameters and view functions arguments. (e.g. `app.route('/<userId'>`) and `def get_user(user_id):`).
 * More coming soon..

To run the plugin, make sure you have IntelliJ IDEA installed with the Plugin SDK and Python plugins. The plugin runs using Gradle for dependencies.
   1. Navigate to the project directory.
   2. type `./gradlew buildPlugin` in the terminal.
   3. type `./gradlew runIde` in the terminal.

Feel free to contribute to this project :)
